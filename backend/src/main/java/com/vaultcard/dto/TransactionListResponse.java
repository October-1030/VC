package com.vaultcard.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TransactionListResponse {
    private List<Transaction> transactions;
    private Integer total;
    private Boolean hasMore;

    @Data
    @Builder
    public static class Transaction {
        private String id;
        private String merchant;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String type;  // "income", "expense"
        private LocalDateTime createdAt;
    }
}
