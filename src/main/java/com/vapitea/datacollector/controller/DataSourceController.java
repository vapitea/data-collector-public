package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.service.DataSourceService;
import com.vapitea.datacollector.service.ParameterService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class DataSourceController {
    private final DataSourceService dataSourceService;
    private final ParameterService parameterService;

    public DataSourceController(DataSourceService dataSourceService, ParameterService parameterService) {
        this.dataSourceService = dataSourceService;
        this.parameterService = parameterService;
    }

    @GetMapping("api/v1.0/dataSources/{id}")
    public DataSource getDataSource(@PathVariable Long id) {
        return dataSourceService.getOne(id);
    }

    @DeleteMapping("/api/v1.0/dataSources/{id}")
    public void deleteDataSource(@PathVariable Long id, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        dataSourceService.deleteDataSource(id);
    }

    @GetMapping("/api/v1.0/dataSources/{id}/parameters")
    public List<Parameter> getParametersOfDataSource(@PathVariable Long id) {
        return dataSourceService.getOneWithParameters(id).getParameters();
    }

    @PutMapping("/api/v1.0/dataSources/{id}")
    public DataSource modifyDataSource(@PathVariable Long id, @RequestBody DataSource dataSource, HttpServletResponse response) {
        if (!id.equals(dataSource.getId())) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        return dataSourceService.modifyDataSource(dataSource);
    }


    @DeleteMapping("/api/v1.0/dataSources/{dataSourceId}/parameters/{parameterId}")
    public void deleteParameterOfDataSource(@PathVariable Long dataSourceId, @PathVariable Long parameterId) {
        parameterService.deleteParameter(parameterId);
    }

}