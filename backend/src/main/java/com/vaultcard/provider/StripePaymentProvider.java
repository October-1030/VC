package com.vaultcard.provider;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.issuing.Card;
import com.stripe.model.issuing.Cardholder;
import com.stripe.model.issuing.Transaction;
import com.stripe.net.Webhook;
import com.stripe.param.*;
import com.stripe.param.issuing.*;
import com.vaultcard.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stripe 支付服务实现
 *
 * 这个类封装了所有Stripe API调用
 * 外部代码不需要知道Stripe的存在
 * 如果要切换到Marqeta，只需实现另一个Provider即可
 */
@Slf4j
@Component("stripeProvider")
public class StripePaymentProvider implements PaymentProvider {

    @Value("${payment.provider.stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${payment.provider.stripe.webhook-secret}")
    private String webhookSecret;

    @Value("${payment.exchange-rate.usd-cny:7.26}")
    private BigDecimal usdToCnyRate;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
        log.info("Stripe provider initialized");
    }

    @Override
    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) {
        try {
            // 创建Stripe PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (request.getAmountUSD().doubleValue() * 100))  // 转为cents
                .setCurrency("usd")
                .addPaymentMethodType(request.getPaymentMethod())  // alipay, card, wechat_pay
                .setDescription(request.getDescription())
                .putMetadata("user_id", request.getUserId())
                .putMetadata("provider", "stripe")
                .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // 返回统一的响应，隐藏Stripe细节
            return PaymentIntentResponse.builder()
                .paymentId(intent.getId())
                .clientSecret(intent.getClientSecret())
                .amountUSD(request.getAmountUSD())
                .estimatedCNY(request.getAmountUSD().multiply(usdToCnyRate))
                .status(intent.getStatus())
                .paymentUrl(extractPaymentUrl(intent))
                .build();

        } catch (StripeException e) {
            log.error("Failed to create payment intent", e);
            throw new RuntimeException("Payment creation failed: " + e.getMessage());
        }
    }

    @Override
    public CardResponse createCard(CreateCardRequest request) {
        try {
            // 1. 先创建或获取Cardholder
            Cardholder cardholder = getOrCreateCardholder(request.getUserId(), request.getCardholderName());

            // 2. 创建虚拟卡
            CardCreateParams cardParams = CardCreateParams.builder()
                .setCardholder(cardholder.getId())
                .setType(CardCreateParams.Type.VIRTUAL)
                .setStatus(CardCreateParams.Status.ACTIVE)
                .setSpendingControls(
                    CardCreateParams.SpendingControls.builder()
                        .addSpendingLimit(
                            CardCreateParams.SpendingControls.SpendingLimit.builder()
                                .setAmount((long) (request.getSpendingLimit().doubleValue() * 100))
                                .setInterval(CardCreateParams.SpendingControls.SpendingLimit.Interval.MONTHLY)
                                .build()
                        )
                        .build()
                )
                .putMetadata("user_id", request.getUserId())
                .putMetadata("card_type", request.getCardType())
                .build();

            Card card = Card.create(cardParams);

            // 返回统一格式，隐藏Stripe的内部结构
            return mapStripeCardToResponse(card);

        } catch (StripeException e) {
            log.error("Failed to create card", e);
            throw new RuntimeException("Card creation failed: " + e.getMessage());
        }
    }

    @Override
    public CardResponse getCard(String cardId) {
        try {
            Card card = Card.retrieve(cardId);
            return mapStripeCardToResponse(card);
        } catch (StripeException e) {
            log.error("Failed to retrieve card", e);
            throw new RuntimeException("Card retrieval failed: " + e.getMessage());
        }
    }

    @Override
    public CardResponse updateCardStatus(String cardId, boolean freeze) {
        try {
            Card card = Card.retrieve(cardId);

            CardUpdateParams params = CardUpdateParams.builder()
                .setStatus(freeze ?
                    CardUpdateParams.Status.INACTIVE :
                    CardUpdateParams.Status.ACTIVE)
                .build();

            card = card.update(params);
            return mapStripeCardToResponse(card);

        } catch (StripeException e) {
            log.error("Failed to update card status", e);
            throw new RuntimeException("Card update failed: " + e.getMessage());
        }
    }

    @Override
    public TransactionListResponse listTransactions(TransactionListRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();
            if (request.getCardId() != null) {
                params.put("card", request.getCardId());
            }
            params.put("limit", request.getLimit());

            TransactionCollection transactions = Transaction.list(params);

            List<TransactionListResponse.Transaction> list = transactions.getData()
                .stream()
                .map(this::mapStripeTransactionToResponse)
                .collect(Collectors.toList());

            return TransactionListResponse.builder()
                .transactions(list)
                .total((int) transactions.getData().size())
                .hasMore(transactions.getHasMore())
                .build();

        } catch (StripeException e) {
            log.error("Failed to list transactions", e);
            throw new RuntimeException("Transaction listing failed: " + e.getMessage());
        }
    }

    @Override
    public String getProviderName() {
        return "stripe";
    }

    @Override
    public boolean verifyWebhookSignature(String payload, String signature) {
        try {
            Webhook.constructEvent(payload, signature, webhookSecret);
            return true;
        } catch (Exception e) {
            log.error("Webhook signature verification failed", e);
            return false;
        }
    }

    @Override
    public WebhookResult handleWebhook(String payload) {
        try {
            Event event = Event.GSON.fromJson(payload, Event.class);

            log.info("Processing webhook event: {}", event.getType());

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    return handlePaymentSuccess(event);
                case "issuing_card.created":
                    return handleCardCreated(event);
                case "issuing_transaction.created":
                    return handleTransaction(event);
                default:
                    log.info("Unhandled event type: {}", event.getType());
                    return WebhookResult.builder()
                        .eventType(event.getType())
                        .success(true)
                        .message("Event logged but not processed")
                        .build();
            }

        } catch (Exception e) {
            log.error("Failed to process webhook", e);
            return WebhookResult.builder()
                .success(false)
                .message("Webhook processing failed: " + e.getMessage())
                .build();
        }
    }

    // ========== Private Helper Methods ==========

    private Cardholder getOrCreateCardholder(String userId, String name) throws StripeException {
        // 先查询是否已存在
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 1);
        CardholderCollection cardholders = Cardholder.list(params);

        if (!cardholders.getData().isEmpty()) {
            return cardholders.getData().get(0);
        }

        // 不存在则创建
        CardholderCreateParams createParams = CardholderCreateParams.builder()
            .setName(name)
            .setEmail(userId + "@vaultcard.com")  // 使用userId作为email
            .setType(CardholderCreateParams.Type.INDIVIDUAL)
            .setBilling(
                CardholderCreateParams.Billing.builder()
                    .setAddress(
                        CardholderCreateParams.Billing.Address.builder()
                            .setLine1("123 Main St")
                            .setCity("New York")
                            .setState("NY")
                            .setPostalCode("10001")
                            .setCountry("US")
                            .build()
                    )
                    .build()
            )
            .putMetadata("user_id", userId)
            .build();

        return Cardholder.create(createParams);
    }

    private CardResponse mapStripeCardToResponse(Card card) {
        return CardResponse.builder()
            .cardId(card.getId())
            .cardNumber(card.getNumber())
            .cvv(card.getCvc())
            .expiryMonth(String.valueOf(card.getExpMonth()))
            .expiryYear(String.valueOf(card.getExpYear()))
            .cardholderName(card.getCardholder() != null ?
                card.getCardholder() : "VaultCard User")
            .status(card.getStatus())
            .type("virtual")
            .spendingLimit(new BigDecimal(card.getSpendingControls()
                .getSpendingLimits().get(0).getAmount() / 100.0))
            .build();
    }

    private TransactionListResponse.Transaction mapStripeTransactionToResponse(Transaction t) {
        return TransactionListResponse.Transaction.builder()
            .id(t.getId())
            .merchant(t.getMerchantData() != null ?
                t.getMerchantData().getName() : "Unknown Merchant")
            .amount(new BigDecimal(Math.abs(t.getAmount()) / 100.0))
            .currency(t.getCurrency().toUpperCase())
            .status(t.getStatus())
            .type(t.getAmount() > 0 ? "income" : "expense")
            .createdAt(LocalDateTime.ofEpochSecond(t.getCreated(), 0, ZoneOffset.UTC))
            .build();
    }

    private String extractPaymentUrl(PaymentIntent intent) {
        // 提取Alipay/WeChat Pay的跳转URL
        if (intent.getNextAction() != null &&
            intent.getNextAction().getAlipayHandleRedirect() != null) {
            return intent.getNextAction().getAlipayHandleRedirect().getUrl();
        }
        return null;
    }

    private WebhookResult handlePaymentSuccess(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
        if (intent != null) {
            log.info("Payment succeeded: {}, user: {}",
                intent.getId(),
                intent.getMetadata().get("user_id"));
            // 这里应该调用Service层来更新用户余额
        }
        return WebhookResult.builder()
            .eventType("payment_intent.succeeded")
            .objectId(intent != null ? intent.getId() : null)
            .success(true)
            .message("Payment processed successfully")
            .build();
    }

    private WebhookResult handleCardCreated(Event event) {
        Card card = (Card) event.getDataObjectDeserializer().getObject().orElse(null);
        log.info("Card created: {}", card != null ? card.getId() : null);
        return WebhookResult.builder()
            .eventType("issuing_card.created")
            .objectId(card != null ? card.getId() : null)
            .success(true)
            .build();
    }

    private WebhookResult handleTransaction(Event event) {
        Transaction transaction = (Transaction) event.getDataObjectDeserializer().getObject().orElse(null);
        log.info("Transaction created: {}", transaction != null ? transaction.getId() : null);
        return WebhookResult.builder()
            .eventType("issuing_transaction.created")
            .objectId(transaction != null ? transaction.getId() : null)
            .success(true)
            .build();
    }
}
