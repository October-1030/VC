package com.vaultcard.service;

import com.vaultcard.dto.*;
import com.vaultcard.provider.PaymentProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 支付服务层
 *
 * 这一层隐藏了Provider的细节
 * 可以通过配置文件随时切换Provider
 */
@Slf4j
@Service
public class PaymentService {

    private final PaymentProvider paymentProvider;

    @Value("${payment.provider.active}")
    private String activeProvider;

    /**
     * 通过Spring的@Qualifier注解动态注入Provider
     * 配置文件中 payment.provider.active=stripe 时注入StripeProvider
     * 配置文件中 payment.provider.active=marqeta 时注入MarqetaProvider
     */
    @Autowired
    public PaymentService(@Qualifier("${payment.provider.active}Provider") PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public PaymentIntentResponse createPayment(PaymentIntentRequest request) {
        log.info("Creating payment for user {} using provider: {}",
            request.getUserId(), paymentProvider.getProviderName());

        // TODO: 这里可以添加业务逻辑
        // - 检查用户KYC状态
        // - 检查年度外汇额度
        // - 风控检测
        // - 记录到数据库

        return paymentProvider.createPaymentIntent(request);
    }

    public CardResponse issueCard(CreateCardRequest request) {
        log.info("Issuing card for user {} using provider: {}",
            request.getUserId(), paymentProvider.getProviderName());

        // TODO: 业务逻辑
        // - 检查用户余额是否足够
        // - 检查用户已有卡片数量限制
        // - 记录发卡记录

        return paymentProvider.createCard(request);
    }

    public CardResponse getCardDetails(String cardId, String userId) {
        log.info("Getting card details: {} for user: {}", cardId, userId);

        // TODO: 验证卡片属于该用户
        return paymentProvider.getCard(cardId);
    }

    public CardResponse freezeCard(String cardId, String userId) {
        log.info("Freezing card: {} for user: {}", cardId, userId);

        // TODO: 验证权限
        return paymentProvider.updateCardStatus(cardId, true);
    }

    public CardResponse unfreezeCard(String cardId, String userId) {
        log.info("Unfreezing card: {} for user: {}", cardId, userId);

        // TODO: 验证权限
        return paymentProvider.updateCardStatus(cardId, false);
    }

    public TransactionListResponse getTransactions(TransactionListRequest request) {
        log.info("Getting transactions for user: {}", request.getUserId());

        // TODO: 权限验证
        return paymentProvider.listTransactions(request);
    }

    public WebhookResult handleWebhook(String payload, String signature) {
        // 验证签名
        if (!paymentProvider.verifyWebhookSignature(payload, signature)) {
            log.error("Invalid webhook signature");
            return WebhookResult.builder()
                .success(false)
                .message("Invalid signature")
                .build();
        }

        // 处理webhook
        return paymentProvider.handleWebhook(payload);
    }

    public String getActiveProvider() {
        return paymentProvider.getProviderName();
    }
}
