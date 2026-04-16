package com.flowtest.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class GraphNode {
    private String nodeId;
    private String path;
    private String method;
    private String summary;
    private List<String> tags;
    private boolean requiresAuth;
    private List<ParameterMapping> parameters;
    private Map<String, Object> requestBodySchema;
    private Map<String, Object> responses;
}
