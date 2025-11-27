package com.vaultcard.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentIntentRequest {
    private String userId;
    private BigDecimal amountUSD;
    private String paymentMethod; // "alipay", "card", "wechat_pay"
    private String description;
}
