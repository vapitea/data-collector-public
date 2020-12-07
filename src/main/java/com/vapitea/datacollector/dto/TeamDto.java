package com.vapitea.datacollector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamDto {
  private Long id;
  private String name;
  private String description;
}
