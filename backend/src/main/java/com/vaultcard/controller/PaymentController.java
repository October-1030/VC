package com.vaultcard.controller;

import com.vaultcard.dto.*;
import com.vaultcard.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 支付API控制器
 *
 * 这些API是前端直接调用的接口
 * 前端只知道这些endpoint，完全不知道背后是Stripe还是Marqeta
 */
@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 创建支付
     * POST /api/payment/create
     */
    @PostMapping("/create")
    public ResponseEntity<PaymentIntentResponse> createPayment(
            @RequestBody PaymentIntentRequest request) {
        log.info("Received payment creation request for user: {}", request.getUserId());
        PaymentIntentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 创建虚拟卡
     * POST /api/payment/cards/create
     */
    @PostMapping("/cards/create")
    public ResponseEntity<CardResponse> createCard(
            @RequestBody CreateCardRequest request) {
        log.info("Received card creation request for user: {}", request.getUserId());
        CardResponse response = paymentService.issueCard(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取卡片详情
     * GET /api/payment/cards/{cardId}
     */
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<CardResponse> getCard(
            @PathVariable String cardId,
            @RequestParam String userId) {
        log.info("Received card details request: {}", cardId);
        CardResponse response = paymentService.getCardDetails(cardId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 冻结卡片
     * POST /api/payment/cards/{cardId}/freeze
     */
    @PostMapping("/cards/{cardId}/freeze")
    public ResponseEntity<CardResponse> freezeCard(
            @PathVariable String cardId,
            @RequestParam String userId) {
        log.info("Freezing card: {}", cardId);
        CardResponse response = paymentService.freezeCard(cardId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 解冻卡片
     * POST /api/payment/cards/{cardId}/unfreeze
     */
    @PostMapping("/cards/{cardId}/unfreeze")
    public ResponseEntity<CardResponse> unfreezeCard(
            @PathVariable String cardId,
            @RequestParam String userId) {
        log.info("Unfreezing card: {}", cardId);
        CardResponse response = paymentService.unfreezeCard(cardId, userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取交易列表
     * GET /api/payment/transactions
     */
    @GetMapping("/transactions")
    public ResponseEntity<TransactionListResponse> listTransactions(
            @RequestParam String userId,
            @RequestParam(required = false) String cardId,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(defaultValue = "0") Integer offset) {
        TransactionListRequest request = new TransactionListRequest();
        request.setUserId(userId);
        request.setCardId(cardId);
        request.setLimit(limit);
        request.setOffset(offset);

        TransactionListResponse response = paymentService.getTransactions(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Webhook接收端点
     * POST /api/payment/webhook
     */
    @PostMapping("/webhook")
    public ResponseEntity<WebhookResult> handleWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String stripeSignature,
            @RequestHeader(value = "X-Marqeta-Signature", required = false) String marqetaSignature) {

        String signature = stripeSignature != null ? stripeSignature : marqetaSignature;
        log.info("Received webhook event");

        WebhookResult result = paymentService.handleWebhook(payload, signature);
        return ResponseEntity.ok(result);
    }

    /**
     * 健康检查
     * GET /api/payment/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        String provider = paymentService.getActiveProvider();
        return ResponseEntity.ok("OK - Using provider: " + provider);
    }
}
