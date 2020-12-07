package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.DataSourceDto;
import com.vapitea.datacollector.dto.ParameterDto;
import com.vapitea.datacollector.mapper.DataSourceMapper;
import com.vapitea.datacollector.mapper.ParameterMapper;
import com.vapitea.datacollector.mapper.TeamMapper;
import com.vapitea.datacollector.service.DataSourceService;
import com.vapitea.datacollector.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
public class DataSourceController {
  private final DataSourceService dataSourceService;
  private final ParameterService parameterService;
  private final DataSourceMapper dataSourceMapper;
  private final TeamMapper teamMapper;
  private final ParameterMapper parameterMapper;


  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.DataSource.read') AND @authorizationManager.isOwnDataSource(authentication, #id) )")
  @GetMapping("api/v1.0/dataSources/{id}")
  public DataSourceDto getDataSource(@PathVariable Long id) {
    return dataSourceMapper.dataSourceToDataSourceDto(dataSourceService.getOne(id));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.DataSource.delete') AND @authorizationManager.isOwnDataSource(authentication, #id) )")
  @DeleteMapping("/api/v1.0/dataSources/{id}")
  public void deleteDataSource(@PathVariable Long id, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    dataSourceService.deleteDataSource(id);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.Parameters.read') AND @authorizationManager.isOwnDataSource(authentication, #id) )")
  @GetMapping("/api/v1.0/dataSources/{id}/parameters")
  public List<ParameterDto> getParametersOfDataSource(@PathVariable Long id) {
    return dataSourceService.getOneWithParameters(id).getParameters().stream().map(parameterMapper::parameterToParameterDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.DataSource.update') AND @authorizationManager.isOwnDataSource(authentication, #id) )")
  @PutMapping("/api/v1.0/dataSources/{id}")
  public void modifyDataSource(@PathVariable Long id, @RequestBody DataSourceDto dataSourceDto, HttpServletResponse response) {
    if (!id.equals(dataSourceDto.getId())) {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    } else {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      dataSourceService.modifyDataSource(dataSourceMapper.dataSourceDtoToDataSource(dataSourceDto));
    }
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.Parameter.delete') AND @authorizationManager.isOwnDataSource(authentication, #dataSourceId) )")
  @DeleteMapping("/api/v1.0/dataSources/{dataSourceId}/parameters/{parameterId}")
  public void deleteParameterOfDataSource(@PathVariable Long dataSourceId, @PathVariable Long parameterId, HttpServletResponse response) {
    parameterService.deleteParameter(parameterId);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
