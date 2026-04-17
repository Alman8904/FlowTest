package com.flowtest.API.repository;

import com.flowtest.API.model.GraphNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GraphNodeRepository extends JpaRepository<GraphNode, String> {
    List<GraphNode> findByPath(String path);
    List<GraphNode> findByMethod(String method);
    List<GraphNode> findByTagsContaining(String tag);
}

