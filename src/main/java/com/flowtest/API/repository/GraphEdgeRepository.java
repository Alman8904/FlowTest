package com.flowtest.API.repository;

import com.flowtest.API.model.GraphEdge;
import com.flowtest.API.model.GraphNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GraphEdgeRepository extends JpaRepository<GraphEdge, String> {
    List<GraphEdge> findByFromNode(GraphNode fromNode);
    List<GraphEdge> findByToNode(GraphNode toNode);
    List<GraphEdge> findByFromNodeAndToNode(GraphNode fromNode, GraphNode toNode);
}

