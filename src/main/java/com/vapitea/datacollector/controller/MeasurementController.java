package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.MeasurementDto;
import com.vapitea.datacollector.dto.MeasurementMessage;
import com.vapitea.datacollector.mapper.MeasurementMapper;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
public class MeasurementController {
  private final MeasurementService measurementService;
  private final MeasurementMapper measurementMapper;


  @PostMapping("api/v1.0/measurements")
  @Validated
  public void postMeasurements(@Valid @RequestBody MeasurementMessage measurementMessage, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_CREATED);
    measurementService.handleMeasurementMessage(measurementMessage);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.Measurements.read') AND @authorizationManager.isOwnParameter(authentication, #id) )")
  @GetMapping("/api/v1.0/parameters/{id}/measurements")
  public List<MeasurementDto> getMeasurementsOfParameter(@PathVariable Long id) {
    Parameter parameter = Parameter.builder().id(id).build();
    return measurementService.getMeasurementsOfParameter(parameter).stream().map(measurementMapper::measurementToMeasurementDto).collect(Collectors.toList());
  }
}
