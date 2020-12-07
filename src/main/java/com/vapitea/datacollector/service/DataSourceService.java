package com.vapitea.datacollector.service;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.repository.DataSourceRepository;
import com.vapitea.datacollector.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DataSourceService {
  private final DataSourceRepository dataSourceRepository;
  private final TeamRepository teamRepository;


  @Transactional
  public DataSource createDataSource(DataSource dataSource, Team team) {
    team = teamRepository.findById(team.getId()).orElseThrow();
    dataSource.setTeam(team);
    dataSource = dataSourceRepository.save(dataSource);
    team.getDataSources().add(dataSource);
    return dataSource;
  }

  public void deleteDataSource(Long id) {
    dataSourceRepository.deleteById(id);
  }

  public DataSource getOne(Long id) {
    return dataSourceRepository.findById(id).orElseThrow();
  }

  @Transactional
  public DataSource getOneWithParameters(Long id) {
    DataSource dataSource = dataSourceRepository.findById(id).orElseThrow();
    dataSource.getParameters().size();  //To load lazy collection
    return dataSource;
  }


  @Transactional
  public DataSource modifyDataSource(DataSource dataSource) {
    DataSource oldDataSource = dataSourceRepository.findById(dataSource.getId()).orElseThrow();
    Team team = oldDataSource.getTeam();
    dataSource.setTeam(team);
    //TODO also add parameters maybe?
    return dataSourceRepository.save(dataSource);
  }
}
