package com.vapitea.datacollector.mapper;

import com.vapitea.datacollector.dto.TeamDto;
import com.vapitea.datacollector.model.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
  public TeamDto teamToTeamDto(Team team) {
    return TeamDto.builder()
      .id(team.getId())
      .name(team.getName())
      .description(team.getDescription())
      .build();
  }

  public Team teamDtoToTeam(TeamDto teamDto) {
    return Team.builder()
      .id(teamDto.getId())
      .name(teamDto.getName())
      .description(teamDto.getDescription())
      .build();
  }
}
