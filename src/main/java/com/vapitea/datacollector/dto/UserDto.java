package com.vapitea.datacollector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private Long id;
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String dType;
}
