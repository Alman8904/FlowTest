package com.flowtest.API.repository;

import com.flowtest.API.model.AuthConfig;
import com.flowtest.API.model.AuthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthConfigRepository extends JpaRepository<AuthConfig, String> {
    List<AuthConfig> findByAuthType(AuthType authType);
    List<AuthConfig> findByUsername(String username);
}

