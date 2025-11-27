package com.vaultcard.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateCardRequest {
    private String userId;
    private String cardholderName;
    private BigDecimal spendingLimit;  // 月消费限额
    private String cardType;  // "gold", "platinum", "black"
}
