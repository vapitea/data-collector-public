package com.vapitea.datacollector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParameterDto {
  private Long id;
  private String name;
  private String unit;
  private String description;
}
