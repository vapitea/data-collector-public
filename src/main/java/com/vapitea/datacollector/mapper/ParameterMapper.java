package com.vapitea.datacollector.mapper;

import com.vapitea.datacollector.dto.ParameterDto;
import com.vapitea.datacollector.model.Parameter;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapper {

  public ParameterDto parameterToParameterDto(Parameter parameter) {
    return ParameterDto.builder()
      .id(parameter.getId())
      .name(parameter.getName())
      .unit(parameter.getUnit())
      .description(parameter.getDescription())
      .build();
  }

  public Parameter parameterDtoToParameter(ParameterDto parameterDto) {
    return Parameter.builder()
      .id(parameterDto.getId())
      .name(parameterDto.getName())
      .unit(parameterDto.getUnit())
      .description(parameterDto.getDescription())
      .build();
  }

}
