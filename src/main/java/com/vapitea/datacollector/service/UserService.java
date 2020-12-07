package com.vapitea.datacollector.service;

import com.vapitea.datacollector.dto.UserDto;
import com.vapitea.datacollector.mapper.UserMapper;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.repository.TeamRepository;
import com.vapitea.datacollector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final TeamRepository teamRepository;
  private final UserMapper userMapper;


  @Transactional
  public void addUserToTeam(Long userId, Long teamId) {
    User managedUser = userRepository.findById(userId).orElseThrow();
    Team managedTeam = teamRepository.findById(teamId).orElseThrow();
    managedUser.getTeams().add(managedTeam);
    managedTeam.getUsers().add(managedUser);
  }


  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getOne(Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  public User getOneWithTeams(Long id) {
    return userRepository.getOneWithTeamsFetchedEagerly(id).orElseThrow();

  }

  @Transactional
  public User modifyUser(UserDto changedUser) {

    User user = userRepository.findById(changedUser.getId()).orElseThrow();
    user.setName(changedUser.getName());
    user.setPassword(encodePassword(changedUser.getPassword()));
    return user;
  }

  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id).orElseThrow();
    List<Team> teams = user.getTeams();

    for (Team team : teams) {
      team.getUsers().remove(user);
    }

    userRepository.delete(user);
  }

  @Transactional
  public void removeUserFromTeam(Long userId, Long teamId) {
    User user = userRepository.findById(userId).orElseThrow();
    Team team = teamRepository.findById(teamId).orElseThrow();
    user.getTeams().remove(team);
    team.getUsers().remove(user);
  }


  public User createUser(UserDto userDto) {
    userDto.setPassword(encodePassword(userDto.getPassword()));
    return userRepository.save(userMapper.userDtoToUser(userDto));
  }


  private String encodePassword(String oldPassword) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return "{bcrypt}" + bCryptPasswordEncoder.encode(oldPassword);
  }
}
