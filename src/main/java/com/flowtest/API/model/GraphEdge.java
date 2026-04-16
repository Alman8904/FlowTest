package com.flowtest.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GraphEdge {
    private String edgeId;
    private String fromNodeId;
    private String toNodeId;
    private List<ParameterMapping> mappings;
}
