package com.vapitea.datacollector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataSourceDto {
  private Long id;
  private String uuid;
  private String name;
  private String location;
  private String description;


}
