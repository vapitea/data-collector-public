package com.vapitea.datacollector.service;

import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.repository.TeamRepository;
import com.vapitea.datacollector.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;


  public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
  }

  public Team createTeam(Team team) {
    return teamRepository.save(team);
  }


  public List<Team> getAll() {
    return teamRepository.findAll();
  }

  public Team getOne(Long id) {
    return teamRepository.findById(id).orElseThrow();
  }

  public void deleteTeam(Long id) {
    teamRepository.deleteById(id);
  }

  public void modifyTeam(Team team) {
    teamRepository.save(team);
  }


  @Transactional
  public Team getTeamWithUsers(Long id) {
    Team team = teamRepository.getOne(id);
    team.getUsers().size(); //To load lazy collection
    return team;
  }

  @Transactional
  public void addUserToTeam(Long userId, Long teamId) {
    User managedUser = userRepository.findById(userId).orElseThrow();
    Team managedTeam = teamRepository.findById(teamId).orElseThrow();
    managedTeam.getUsers().add(managedUser);
    managedUser.getTeams().add(managedTeam);
  }

  @Transactional
  public Team getOneWithDataSources(Long id) {
    Team team = teamRepository.findById(id).orElseThrow();
    team.getDataSources().size();
    return team;
  }
}
