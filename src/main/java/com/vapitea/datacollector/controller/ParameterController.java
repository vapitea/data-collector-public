package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
public class ParameterController {
    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }


    @GetMapping("/api/v1.0/parameters/{id}")
    public Parameter getParameter(@PathVariable Long id) {
        return parameterService.getOne(id);
    }

    @DeleteMapping("/api/v1.0/parameters/{id}")
    public void deleteParameter(@PathVariable Long id, HttpServletResponse response) {
        parameterService.deleteParameter(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PutMapping("/api/v1.0/parameters/{id}")
    public void modifyParameter(@PathVariable Long id, @RequestBody Parameter parameter, HttpServletResponse response) {
        if (parameter.getId().equals(id)) {
            parameterService.modifyParameter(parameter);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/api/v1.0/dataSources/{id}/parameters")
    public Parameter createNewParameter(@PathVariable Long id, @RequestBody Parameter parameter, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        DataSource dataSource = DataSource.builder().id(id).build();
        return parameterService.createParameter(parameter, dataSource);
    }

}
