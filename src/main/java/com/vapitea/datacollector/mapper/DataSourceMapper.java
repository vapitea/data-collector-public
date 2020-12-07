package com.vapitea.datacollector.mapper;

import com.vapitea.datacollector.dto.DataSourceDto;
import com.vapitea.datacollector.model.DataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceMapper {

  public DataSourceDto dataSourceToDataSourceDto(DataSource dataSource) {
    return DataSourceDto.builder()
      .id(dataSource.getId())
      .uuid(dataSource.getUuid())
      .name(dataSource.getName())
      .location(dataSource.getLocation())
      .description(dataSource.getDescription())
      .build();
  }

  public DataSource dataSourceDtoToDataSource(DataSourceDto dataSourceDto) {
    return DataSource.builder()
      .id(dataSourceDto.getId())
      .uuid(dataSourceDto.getUuid())
      .name(dataSourceDto.getName())
      .location(dataSourceDto.getLocation())
      .description(dataSourceDto.getDescription())
      .build();
  }

}
