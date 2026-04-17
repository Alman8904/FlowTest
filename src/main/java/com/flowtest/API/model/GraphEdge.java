package com.flowtest.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "graph_edges")
public class GraphEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String edgeId;

    @ManyToOne
    @JoinColumn(name = "from_node_id", nullable = false)
    private GraphNode fromNode;

    @ManyToOne
    @JoinColumn(name = "to_node_id", nullable = false)
    private GraphNode toNode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "edge_id")
    private List<ParameterMapping> mappings;
}
