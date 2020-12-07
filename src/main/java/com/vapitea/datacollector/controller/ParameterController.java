package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.ParameterDto;
import com.vapitea.datacollector.mapper.ParameterMapper;
import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
public class ParameterController {
  private final ParameterService parameterService;
  private final ParameterMapper parameterMapper;

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.Parameter.read') AND @authorizationManager.isOwnParameter(authentication, #id) )")
  @GetMapping("/api/v1.0/parameters/{id}")
  public ParameterDto getParameter(@PathVariable Long id) {
    return parameterMapper.parameterToParameterDto(parameterService.getOne(id));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.Parameter.delete') AND @authorizationManager.isOwnParameter(authentication, #id) )")
  @DeleteMapping("/api/v1.0/parameters/{id}")
  public void deleteParameter(@PathVariable Long id, HttpServletResponse response) {
    parameterService.deleteParameter(id);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.Parameter.update') AND @authorizationManager.isOwnParameter(authentication, #id) )")
  @PutMapping("/api/v1.0/parameters/{id}")
  public void modifyParameter(@PathVariable Long id, @RequestBody ParameterDto parameterDto, HttpServletResponse response) {
    if (parameterDto.getId().equals(id)) {
      parameterService.modifyParameter(parameterMapper.parameterDtoToParameter(parameterDto));
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.Parameter.create') AND @authorizationManager.isOwnDataSource(authentication, #id) )")
  @PostMapping("/api/v1.0/dataSources/{id}/parameters")
  public ParameterDto createNewParameter(@PathVariable Long id, @RequestBody ParameterDto parameterDto, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_CREATED);
    DataSource dataSource = DataSource.builder().id(id).build();
    return parameterMapper.parameterToParameterDto(parameterService.createParameter(parameterMapper.parameterDtoToParameter(parameterDto), dataSource));
  }

}
