package com.vapitea.datacollector.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class MeasurementMessage {

  @NotNull
  @NotEmpty
  private String uuid;
  @NotNull
  private Map<String, Double> payload;
}
