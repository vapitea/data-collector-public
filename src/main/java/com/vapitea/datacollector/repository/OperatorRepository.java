package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
