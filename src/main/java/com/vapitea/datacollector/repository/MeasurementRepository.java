package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {


}
