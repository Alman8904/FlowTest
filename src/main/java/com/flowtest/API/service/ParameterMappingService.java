package com.flowtest.API.service;

import com.flowtest.API.model.ParameterMapping;
import com.flowtest.API.model.GraphEdge;
import com.flowtest.API.model.NodeExecutionResult;
import com.flowtest.API.repository.ParameterMappingRepository;
import com.flowtest.API.repository.NodeExecutionResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParameterMappingService {

    private final ParameterMappingRepository parameterMappingRepository;
    private final NodeExecutionResultRepository resultRepository;

    /**
     * Create a parameter mapping
     */
    public ParameterMapping createMapping(ParameterMapping mapping) {
        log.info("Creating parameter mapping: {} -> {}", mapping.getSourceField(), mapping.getTargetParam());
        return parameterMappingRepository.save(mapping);
    }

    /**
     * Get a mapping by ID
     */
    public Optional<ParameterMapping> getMapping(String mappingId) {
        return parameterMappingRepository.findById(mappingId);
    }

    /**
     * Get all mappings
     */
    public List<ParameterMapping> getAllMappings() {
        return parameterMappingRepository.findAll();
    }

    /**
     * Extract data from previous node result using JSONPath
     * and prepare parameters for next node
     */
    public Map<String, Object> extractAndMapParameters(
            GraphEdge edge,
            NodeExecutionResult previousResult,
            List<ParameterMapping> mappings) {

        log.info("Extracting parameters from edge: {} -> {}",
                edge.getFromNode().getNodeId(),
                edge.getToNode().getNodeId());

        Map<String, Object> nextNodeParams = new HashMap<>();

        if (mappings == null || mappings.isEmpty()) {
            log.warn("No mappings defined for edge");
            return nextNodeParams;
        }

        // Get previous node response data
        Map<String, Object> responseData = parseResponseData(previousResult.getResponseBody());

        // Apply each mapping
        for (ParameterMapping mapping : mappings) {
            try {
                Object extractedValue = extractValueByJsonPath(responseData, mapping.getJsonPath());

                if (extractedValue != null) {
                    nextNodeParams.put(mapping.getTargetParam(), extractedValue);
                    log.debug("Mapped {} to {} = {}",
                            mapping.getSourceField(),
                            mapping.getTargetParam(),
                            extractedValue);
                } else {
                    log.warn("Could not extract value from {}", mapping.getJsonPath());
                }
            } catch (Exception e) {
                log.error("Error applying mapping {}: {}", mapping.getTargetParam(), e.getMessage());
            }
        }

        return nextNodeParams;
    }

    /**
     * Extract value from response using JSONPath-like syntax
     * Simple implementation: supports "key" or "key.subkey" format
     */
    @SuppressWarnings("unchecked")
    private Object extractValueByJsonPath(Map<String, Object> data, String jsonPath) {
        if (jsonPath == null || jsonPath.isEmpty() || data == null) {
            return null;
        }

        String[] parts = jsonPath.split("\\.");
        Object current = data;

        for (String part : parts) {
            if (current instanceof Map) {
                current = ((Map<String, Object>) current).get(part);
            } else {
                return null;
            }
        }

        return current;
    }

    /**
     * Parse response body string to Map
     * TODO: Use Jackson ObjectMapper for better JSON parsing
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseResponseData(String responseBody) {
        // Simple implementation - can be enhanced with Jackson
        if (responseBody == null || responseBody.isEmpty()) {
            return new HashMap<>();
        }

        // TODO: Use com.fasterxml.jackson.databind.ObjectMapper
        // For now, return empty map - to be implemented properly
        return new HashMap<>();
    }

    /**
     * Validate if all required parameters are mapped
     */
    public boolean validateMappings(List<ParameterMapping> mappings) {
        if (mappings == null || mappings.isEmpty()) {
            log.warn("No mappings provided for validation");
            return false;
        }

        for (ParameterMapping mapping : mappings) {
            if (mapping.getTargetParam() == null || mapping.getTargetParam().isEmpty()) {
                log.warn("Invalid mapping: target param is empty");
                return false;
            }
            if ("true".equalsIgnoreCase(mapping.getRequired()) &&
                (mapping.getJsonPath() == null || mapping.getJsonPath().isEmpty())) {
                log.warn("Required mapping {} is missing jsonPath", mapping.getTargetParam());
                return false;
            }
        }

        return true;
    }

    /**
     * Delete a mapping
     */
    public void deleteMapping(String mappingId) {
        log.info("Deleting mapping: {}", mappingId);
        parameterMappingRepository.deleteById(mappingId);
    }

    /**
     * Update a mapping
     */
    public ParameterMapping updateMapping(String mappingId, ParameterMapping mappingDetails) {
        ParameterMapping mapping = parameterMappingRepository.findById(mappingId)
                .orElseThrow(() -> new RuntimeException("Mapping not found: " + mappingId));

        if (mappingDetails.getSourceField() != null) mapping.setSourceField(mappingDetails.getSourceField());
        if (mappingDetails.getTargetParam() != null) mapping.setTargetParam(mappingDetails.getTargetParam());
        if (mappingDetails.getJsonPath() != null) mapping.setJsonPath(mappingDetails.getJsonPath());
        if (mappingDetails.getTargetParamType() != null) mapping.setTargetParamType(mappingDetails.getTargetParamType());
        if (mappingDetails.getRequired() != null) mapping.setRequired(mappingDetails.getRequired());

        return parameterMappingRepository.save(mapping);
    }
}

