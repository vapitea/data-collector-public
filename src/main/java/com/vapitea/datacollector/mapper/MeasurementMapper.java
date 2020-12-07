package com.vapitea.datacollector.mapper;

import com.vapitea.datacollector.dto.MeasurementDto;
import com.vapitea.datacollector.model.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {

  public MeasurementDto measurementToMeasurementDto(Measurement measurement) {
    return MeasurementDto.builder()
      .id(measurement.getId())
      .timestamp(measurement.getTimestamp())
      .value(measurement.getValue())
      .build();
  }

  Measurement measurementDtoToMeasurement(MeasurementDto measurementDto) {
    return Measurement.builder()
      .id(measurementDto.getId())
      .timestamp(measurementDto.getTimestamp())
      .value(measurementDto.getValue())
      .build();
  }

}
