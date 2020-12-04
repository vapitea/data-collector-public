package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.MeasurementMessage;
import com.vapitea.datacollector.model.Measurement;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.service.MeasurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@Slf4j
public class MeasurementController {
    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }


    @PostMapping("api/v1.0/measurements")
    @Validated
    public void postMeasurements(@Valid @RequestBody MeasurementMessage measurementMessage, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        measurementService.handleMeasurementMessage(measurementMessage);
    }

    @GetMapping("/api/v1.0/parameters/{id}/measurements")
    public List<Measurement> getMeasurementsOfParameter(@PathVariable Long id) {
        Parameter parameter = Parameter.builder().id(id).build();
        return measurementService.getMeasurementsOfParameter(parameter);
    }
}
