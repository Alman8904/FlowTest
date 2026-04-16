package com.flowtest.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NodeExecutionResult {
    private String nodeId;
    private int statusCode;
    private Map<String, Object> responseBody;
    private String rawResponseBody;
    private long executionTimeMs;
    private String errorMessage;
    private boolean success;
    private LocalDateTime executedAt;
}
