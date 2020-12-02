package com.vapitea.datacollector.service;

import com.vapitea.datacollector.dto.MeasurementMessage;
import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Measurement;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.repository.DataSourceRepository;
import com.vapitea.datacollector.repository.MeasurementRepository;
import com.vapitea.datacollector.repository.ParameterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final ParameterRepository parameterRepository;
    private final DataSourceRepository dataSourceRepository;

    public MeasurementService(MeasurementRepository measurementRepository, ParameterRepository parameterRepository, DataSourceRepository dataSourceRepository) {
        this.measurementRepository = measurementRepository;
        this.parameterRepository = parameterRepository;
        this.dataSourceRepository = dataSourceRepository;
    }

    @Transactional
    public Measurement createMeasurement(LocalDateTime timestamp, Double value, Parameter parameter) {
        Measurement measurement = new Measurement();
        measurement.setTimestamp(timestamp);
        measurement.setValue(value);

        parameter = parameterRepository.findById(parameter.getId()).orElseThrow();
        measurement.setParameter(parameter);
        return measurementRepository.save(measurement);
    }

    @Transactional
    public void handleMeasurementMessage(MeasurementMessage measurementMessage) {
        DataSource dataSource = dataSourceRepository.findByUuid(measurementMessage.getUuid()).orElseThrow();

        for (Map.Entry<String, Double> keyValuePair : measurementMessage.getPayload().entrySet()) {
            Parameter parameter = findMatchingParameterIfExists(dataSource, keyValuePair.getKey());
            if (parameter == null) {
                continue;
            }
            saveMeasurement(parameter, keyValuePair.getValue(), LocalDateTime.now());

        }

    }

    private void saveMeasurement(Parameter parameter, Double value, LocalDateTime timestamp) {
        Measurement measurement = new Measurement();
        measurement.setParameter(parameter);
        measurement.setValue(value);
        measurement.setTimestamp(timestamp);

        measurementRepository.save(measurement);
    }

    private Parameter findMatchingParameterIfExists(DataSource dataSource, String key) {
        return dataSource.getParameters()
                .stream()
                .filter(parameter -> parameter.getName().equals(key))
                .findFirst()
                .orElse(null);
    }


    @Transactional
    public List<Measurement> getMeasurementsOfParameter(Parameter parameter) {
        parameter = parameterRepository.findById(parameter.getId()).orElseThrow();
        parameter.getMeasurements().size();
        return parameter.getMeasurements();
    }
}
