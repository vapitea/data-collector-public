package com.vapitea.datacollector.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeasurementDto {
  private Long id;
  private LocalDateTime timestamp;
  private Double value;
}
