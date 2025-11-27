package com.vaultcard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebhookResult {
    private String eventType;
    private String objectId;
    private Boolean success;
    private String message;
}
