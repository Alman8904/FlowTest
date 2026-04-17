package com.flowtest.API.repository;

import com.flowtest.API.model.ParameterMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterMappingRepository extends JpaRepository<ParameterMapping, String> {
}

