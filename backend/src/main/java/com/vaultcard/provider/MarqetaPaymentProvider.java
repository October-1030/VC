package com.vaultcard.provider;

import com.vaultcard.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Marqeta 支付服务实现（备用方案）
 *
 * 当需要从Stripe切换到Marqeta时，只需：
 * 1. 在配置文件中改 payment.provider.active=marqeta
 * 2. 前端代码无需任何修改！
 *
 * 这就是抽象层的强大之处
 */
@Slf4j
@Component("marqetaProvider")
public class MarqetaPaymentProvider implements PaymentProvider {

    @Value("${payment.provider.marqeta.app-token:}")
    private String appToken;

    @Value("${payment.provider.marqeta.admin-token:}")
    private String adminToken;

    @Override
    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) {
        // TODO: 实现Marqeta的支付逻辑
        // 调用Marqeta API: POST /v3/fundingsources
        log.info("Creating payment intent with Marqeta for user: {}", request.getUserId());
        throw new UnsupportedOperationException("Marqeta integration not yet implemented");
    }

    @Override
    public CardResponse createCard(CreateCardRequest request) {
        // TODO: 实现Marqeta的发卡逻辑
        // 调用Marqeta API:
        // 1. POST /v3/users (创建用户)
        // 2. POST /v3/fundingsources (创建资金源)
        // 3. POST /v3/cardproducts (创建卡产品)
        // 4. POST /v3/cards (创建卡)
        log.info("Creating card with Marqeta for user: {}", request.getUserId());
        throw new UnsupportedOperationException("Marqeta integration not yet implemented");
    }

    @Override
    public CardResponse getCard(String cardId) {
        // TODO: GET /v3/cards/{token}
        throw new UnsupportedOperationException("Marqeta integration not yet implemented");
    }

    @Override
    public CardResponse updateCardStatus(String cardId, boolean freeze) {
        // TODO: PUT /v3/cards/{token}
        throw new UnsupportedOperationException("Marqeta integration not yet implemented");
    }

    @Override
    public TransactionListResponse listTransactions(TransactionListRequest request) {
        // TODO: GET /v3/transactions
        throw new UnsupportedOperationException("Marqeta integration not yet implemented");
    }

    @Override
    public String getProviderName() {
        return "marqeta";
    }

    @Override
    public boolean verifyWebhookSignature(String payload, String signature) {
        // TODO: 实现Marqeta的webhook验证
        return false;
    }

    @Override
    public WebhookResult handleWebhook(String payload) {
        // TODO: 处理Marqeta的webhook
        throw new UnsupportedOperationException("Marqeta webhook handling not yet implemented");
    }
}
