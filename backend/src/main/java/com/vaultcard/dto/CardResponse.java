package com.vaultcard.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CardResponse {
    private String cardId;
    private String cardNumber;
    private String cvv;
    private String expiryMonth;
    private String expiryYear;
    private String cardholderName;
    private String status;  // "active", "frozen", "cancelled"
    private String type;    // "virtual", "physical"
    private BigDecimal balance;
    private BigDecimal spendingLimit;
}
