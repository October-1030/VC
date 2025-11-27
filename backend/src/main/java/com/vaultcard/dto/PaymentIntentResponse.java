package com.vaultcard.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentIntentResponse {
    private String paymentId;
    private String clientSecret;
    private BigDecimal amountUSD;
    private BigDecimal estimatedCNY;  // 预估人民币金额
    private String status;
    private String paymentUrl;  // 用于跳转的URL（Alipay/WeChat）
}
