package com.vapitea.datacollector.security;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Parameter;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.repository.DataSourceRepository;
import com.vapitea.datacollector.repository.ParameterRepository;
import com.vapitea.datacollector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class AuthorizationManager {
//TODO replace these very inefficient jpa queries

  private final UserRepository userRepository;
  private final DataSourceRepository dataSourceRepository;
  private final ParameterRepository parameterRepository;

  public boolean isOwnUser(Authentication authentication, Long requestedUserId) {
    User authenticatedUser = (User) authentication.getPrincipal();
    return authenticatedUser.getId().equals(requestedUserId);
  }

  @Transactional
  public boolean isOwnTeam(Authentication authentication, Long requestedTeamId) {
    User authenticatedUser = (User) authentication.getPrincipal();
    User user = userRepository.findById(authenticatedUser.getId()).orElseThrow();
    Team requestedTeam = Team.builder().id(requestedTeamId).build();
    return user.getTeams().contains(requestedTeam);
  }

  @Transactional
  public boolean isOwnDataSource(Authentication authentication, Long requestedDataSourceId) {
    User authenticatedUser = (User) authentication.getPrincipal();
    User user = userRepository.findById(authenticatedUser.getId()).orElseThrow();
    DataSource dataSource = dataSourceRepository.findById(requestedDataSourceId).orElseThrow();
    return dataSource.getTeam().getUsers().contains(user);
  }

  @Transactional
  public boolean isOwnParameter(Authentication authentication, Long requestedParameterId) {
    User authenticatedUser = (User) authentication.getPrincipal();
    User user = userRepository.findById(authenticatedUser.getId()).orElseThrow();
    Parameter parameter = parameterRepository.findById(requestedParameterId).orElseThrow();
    return parameter.getDataSource().getTeam().getUsers().contains(user);
  }


}
