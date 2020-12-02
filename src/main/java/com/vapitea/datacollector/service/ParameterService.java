package com.vapitea.datacollector.service;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.repository.DataSourceRepository;
import com.vapitea.datacollector.repository.ParameterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;
    private final DataSourceRepository dataSourceRepository;

    public ParameterService(ParameterRepository parameterRepository, DataSourceRepository dataSourceRepository) {
        this.parameterRepository = parameterRepository;
        this.dataSourceRepository = dataSourceRepository;
    }

    @Transactional
    public Parameter createParameter(Parameter parameter, DataSource dataSource) {
        dataSource = dataSourceRepository.findById(dataSource.getId()).orElseThrow();
        parameter.setDataSource(dataSource);
        dataSource.getParameters().add(parameter);
        return parameter;
    }

    public void deleteParameter(Long parameterId) {
        parameterRepository.deleteById(parameterId);
    }

    public Parameter getOne(Long id) {
        return parameterRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Parameter modifyParameter(Parameter parameter) {
        Parameter oldParameter = parameterRepository.findById(parameter.getId()).orElseThrow();
        DataSource dataSource = oldParameter.getDataSource();
        parameter.setDataSource(dataSource);
        return parameterRepository.save(parameter);
    }
}
