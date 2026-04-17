package com.flowtest.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "node_execution_results")
public class NodeExecutionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resultId;

    @ManyToOne
    @JoinColumn(name = "node_id", nullable = false)
    private GraphNode node;

    private int statusCode;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Column(columnDefinition = "TEXT")
    private String rawResponseBody;

    private long executionTimeMs;
    private String errorMessage;
    private boolean success;
    private LocalDateTime executedAt;
}
