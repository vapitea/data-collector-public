package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
