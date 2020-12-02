package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

    public Optional<DataSource> findByUuid(String uuid);
}
