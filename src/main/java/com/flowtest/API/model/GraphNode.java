package com.flowtest.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "graph_nodes")
public class GraphNode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String nodeId;

    private String path;
    private String method;
    private String summary;

    @ElementCollection
    @CollectionTable(name = "node_tags", joinColumns = @JoinColumn(name = "node_id"))
    @Column(name = "tag")
    private List<String> tags;

    private boolean requiresAuth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "node_id")
    private List<ParameterMapping> parameters;

    @Column(columnDefinition = "TEXT")
    private String requestBodySchema;

    @Column(columnDefinition = "TEXT")
    private String responses;

    @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL)
    private List<GraphEdge> outgoingEdges;

    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
    private List<NodeExecutionResult> executionResults;
}
