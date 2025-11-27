package com.vaultcard.dto;

import lombok.Data;

@Data
public class TransactionListRequest {
    private String userId;
    private String cardId;
    private Integer limit = 20;
    private Integer offset = 0;
}
