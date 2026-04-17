package com.flowtest.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "parameter_mappings")
public class ParameterMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mappingId;

    private String sourceField;
    private String targetParam;
    private String jsonPath;
    private String targetParamType;
    private String required;
}
