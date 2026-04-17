package com.flowtest.API.repository;

import com.flowtest.API.model.NodeExecutionResult;
import com.flowtest.API.model.GraphNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NodeExecutionResultRepository extends JpaRepository<NodeExecutionResult, String> {
    List<NodeExecutionResult> findByNode(GraphNode node);
    List<NodeExecutionResult> findBySuccess(boolean success);
    List<NodeExecutionResult> findByExecutedAtAfter(LocalDateTime executedAt);
    List<NodeExecutionResult> findByNodeOrderByExecutedAtDesc(GraphNode node);
}

