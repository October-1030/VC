package com.vaultcard.provider;

import com.vaultcard.dto.*;
import java.math.BigDecimal;

/**
 * 支付服务提供商抽象接口
 *
 * 这个接口隐藏了底层实现（Stripe/Marqeta/Lithic等）
 * 前端只需要调用统一的API，不知道具体使用哪个服务商
 *
 * 可以随时切换实现而不影响前端代码
 */
public interface PaymentProvider {

    /**
     * 创建支付意图（用于充值）
     * @param request 支付请求
     * @return 支付响应（包含客户端密钥）
     */
    PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request);

    /**
     * 创建虚拟卡
     * @param request 创卡请求
     * @return 卡片信息
     */
    CardResponse createCard(CreateCardRequest request);

    /**
     * 获取卡片详情
     * @param cardId 卡片ID
     * @return 卡片信息
     */
    CardResponse getCard(String cardId);

    /**
     * 冻结/解冻卡片
     * @param cardId 卡片ID
     * @param freeze true=冻结，false=解冻
     * @return 更新后的卡片信息
     */
    CardResponse updateCardStatus(String cardId, boolean freeze);

    /**
     * 获取交易列表
     * @param request 查询请求
     * @return 交易列表
     */
    TransactionListResponse listTransactions(TransactionListRequest request);

    /**
     * 获取当前提供商名称
     * @return 提供商名称（如 "stripe", "marqeta"）
     */
    String getProviderName();

    /**
     * 验证webhook签名
     * @param payload webhook负载
     * @param signature 签名
     * @return 是否有效
     */
    boolean verifyWebhookSignature(String payload, String signature);

    /**
     * 处理webhook事件
     * @param payload webhook负载
     * @return 处理结果
     */
    WebhookResult handleWebhook(String payload);
}
