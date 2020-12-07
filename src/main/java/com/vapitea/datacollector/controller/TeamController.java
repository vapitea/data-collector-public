package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.DataSourceDto;
import com.vapitea.datacollector.dto.TeamDto;
import com.vapitea.datacollector.dto.UserDto;
import com.vapitea.datacollector.mapper.DataSourceMapper;
import com.vapitea.datacollector.mapper.TeamMapper;
import com.vapitea.datacollector.mapper.UserMapper;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.service.DataSourceService;
import com.vapitea.datacollector.service.TeamService;
import com.vapitea.datacollector.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RestController
public class TeamController {

  private final TeamService teamService;
  private final UserService userService;
  private final DataSourceService dataSourceService;
  private final UserMapper userMapper;
  private final TeamMapper teamMapper;
  private final DataSourceMapper dataSourceMapper;


  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @GetMapping("api/v1.0/teams")
  public List<TeamDto> getTeams() {
    return teamService.getAll().stream().map(teamMapper::teamToTeamDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.Team.read') AND @authorizationManager.isOwnTeam(authentication, #id) )")
  @GetMapping("api/v1.0/teams/{id}")
  public TeamDto getTeam(@PathVariable Long id) {
    return teamMapper.teamToTeamDto(teamService.getOne(id));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @DeleteMapping("api/v1.0/teams/{id}")
  public void deleteTeam(@PathVariable Long id, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    teamService.deleteTeam(id);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PutMapping("api/v1.0/teams/{id}")
  public void modifyTeam(@PathVariable Long id, @RequestBody TeamDto teamDto, HttpServletResponse response) {
    if (!id.equals(teamDto.getId())) {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
    teamService.modifyTeam(teamMapper.teamDtoToTeam(teamDto));
    response.setStatus(HttpServletResponse.SC_ACCEPTED);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.Team.Users.read') AND @authorizationManager.isOwnTeam(authentication, #id) )")
  @GetMapping("api/v1.0/teams/{id}/users")
  public List<UserDto> getUsersOfTeam(@PathVariable Long id) {
    return teamService.getTeamWithUsers(id).getUsers().stream().map(userMapper::userToUserDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @DeleteMapping("api/v1.0/teams/{teamId}/users/{userId}")
  public void removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId, HttpServletResponse response) {
    userService.removeUserFromTeam(userId, teamId);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PostMapping("/api/v1.0/teams/{teamId}/users/{userId}")
  public void addTeamToUser(@PathVariable Long teamId, @PathVariable Long userId, HttpServletResponse response) {
    teamService.addUserToTeam(userId, teamId);
    response.setStatus(HttpServletResponse.SC_CREATED);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.DataSources.read') AND @authorizationManager.isOwnTeam(authentication, #id) )")
  @GetMapping("/api/v1.0/teams/{id}/dataSources")
  public List<DataSourceDto> getDataSourcesOfTeam(@PathVariable Long id) {
    return teamService.getOneWithDataSources(id).getDataSources()
      .stream().map(dataSourceMapper::dataSourceToDataSourceDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.DataSource.delete') AND @authorizationManager.isOwnTeam(authentication, #teamId) )")
  @DeleteMapping("/api/v1.0/teams/{teamId}/dataSources/{dataSourceId}")
  public void deleteDataSourceOfTeam(@PathVariable Long teamId, @PathVariable Long dataSourceId, HttpServletResponse response) {
    dataSourceService.deleteDataSource(dataSourceId);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PostMapping("/api/v1.0/teams")
  public TeamDto createNewTeam(@RequestBody TeamDto teamDto, HttpServletResponse response) {
    if (teamDto.getId() != null) {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      return null;
    }
    response.setStatus(HttpServletResponse.SC_CREATED);
    return teamMapper.teamToTeamDto(teamService.createTeam(teamMapper.teamDtoToTeam(teamDto)));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('OPERATOR.DataSource.create') AND @authorizationManager.isOwnTeam(authentication, #id) )")
  @PostMapping("/api/v1.0/teams/{id}/dataSources")
  public DataSourceDto createNewDataSource(@PathVariable Long id, @RequestBody DataSourceDto dataSourceDto, HttpServletResponse response) {
    Team team = Team.builder().id(id).build();
    response.setStatus(HttpServletResponse.SC_CREATED);
    return dataSourceMapper.dataSourceToDataSourceDto(dataSourceService.createDataSource(dataSourceMapper.dataSourceDtoToDataSource(dataSourceDto), team));
  }

}


