package com.flowtest.API.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ParameterMapping {
    private String sourceField;
    private String targetParam;
    private String jsonPath;
    private String targetParamType;
    private String required;
}
