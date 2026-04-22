package com.flowtest.API.service;

import com.flowtest.API.model.GraphNode;
import com.flowtest.API.model.GraphEdge;
import com.flowtest.API.repository.GraphNodeRepository;
import com.flowtest.API.repository.GraphEdgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GraphService {
    
    private final GraphNodeRepository nodeRepository;
    private final GraphEdgeRepository edgeRepository;
    
    /**
     * Create a new graph node (endpoint)
     */
    public GraphNode createNode(GraphNode node) {
        log.info("Creating new graph node: {} {}", node.getMethod(), node.getPath());
        return nodeRepository.save(node);
    }
    
    /**
     * Get a node by ID
     */
    public Optional<GraphNode> getNode(String nodeId) {
        return nodeRepository.findById(nodeId);
    }
    
    /**
     * Get all nodes
     */
    public List<GraphNode> getAllNodes() {
        return nodeRepository.findAll();
    }
    
    /**
     * Get nodes by HTTP method (GET, POST, etc)
     */
    public List<GraphNode> getNodesByMethod(String method) {
        return nodeRepository.findByMethod(method);
    }
    
    /**
     * Get nodes by path
     */
    public List<GraphNode> getNodesByPath(String path) {
        return nodeRepository.findByPath(path);
    }
    
    /**
     * Update a node
     */
    public GraphNode updateNode(String nodeId, GraphNode nodeDetails) {
        log.info("Updating node: {}", nodeId);
        GraphNode node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found: " + nodeId));
        
        if (nodeDetails.getPath() != null) node.setPath(nodeDetails.getPath());
        if (nodeDetails.getMethod() != null) node.setMethod(nodeDetails.getMethod());
        if (nodeDetails.getSummary() != null) node.setSummary(nodeDetails.getSummary());
        if (nodeDetails.getTags() != null) node.setTags(nodeDetails.getTags());
        node.setRequiresAuth(nodeDetails.isRequiresAuth());
        if (nodeDetails.getParameters() != null) node.setParameters(nodeDetails.getParameters());
        if (nodeDetails.getRequestBodySchema() != null) node.setRequestBodySchema(nodeDetails.getRequestBodySchema());
        if (nodeDetails.getResponses() != null) node.setResponses(nodeDetails.getResponses());
        
        return nodeRepository.save(node);
    }
    
    /**
     * Delete a node
     */
    public void deleteNode(String nodeId) {
        log.info("Deleting node: {}", nodeId);
        nodeRepository.deleteById(nodeId);
    }
    
    /**
     * Create an edge (connection) between two nodes
     */
    public GraphEdge createEdge(String fromNodeId, String toNodeId) {
        log.info("Creating edge from {} to {}", fromNodeId, toNodeId);
        
        GraphNode fromNode = nodeRepository.findById(fromNodeId)
                .orElseThrow(() -> new RuntimeException("From node not found: " + fromNodeId));
        GraphNode toNode = nodeRepository.findById(toNodeId)
                .orElseThrow(() -> new RuntimeException("To node not found: " + toNodeId));
        
        GraphEdge edge = GraphEdge.builder()
                .fromNode(fromNode)
                .toNode(toNode)
                .build();
        
        return edgeRepository.save(edge);
    }
    
    /**
     * Get edges from a node
     */
    public List<GraphEdge> getOutgoingEdges(String nodeId) {
        GraphNode node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found: " + nodeId));
        return edgeRepository.findByFromNode(node);
    }
    
    /**
     * Get edges to a node
     */
    public List<GraphEdge> getIncomingEdges(String nodeId) {
        GraphNode node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Node not found: " + nodeId));
        return edgeRepository.findByToNode(node);
    }
    
    /**
     * Get an edge by ID
     */
    public Optional<GraphEdge> getEdge(String edgeId) {
        return edgeRepository.findById(edgeId);
    }
    
    /**
     * Delete an edge
     */
    public void deleteEdge(String edgeId) {
        log.info("Deleting edge: {}", edgeId);
        edgeRepository.deleteById(edgeId);
    }
}

