package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

   Optional<DataSource> findByUuid(String uuid);
}
