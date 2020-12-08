package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.TeamDto;
import com.vapitea.datacollector.dto.UserDto;
import com.vapitea.datacollector.mapper.TeamMapper;
import com.vapitea.datacollector.mapper.UserMapper;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;
  private final TeamMapper teamMapper;


  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @GetMapping("api/v1.0/users")
  public List<UserDto> getUsers() {
    return userService.getAll().stream().map(userMapper::userToUserDto).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.User.read') AND @authorizationManager.isOwnUser(authentication, #id) )")
  @GetMapping("api/v1.0/users/{id}")
  public UserDto getUser(@PathVariable Long id) {
    return userMapper.userToUserDto(userService.getOne(id));
  }


  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR  hasAuthority('USER.User.readOwn')")
  @GetMapping("api/v1.0/ownUser")
  public UserDto getUser(@AuthenticationPrincipal User user) {
    return userMapper.userToUserDto(userService.getOne(user.getId()));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PostMapping("api/v1.0/users")
  public UserDto createUser(@RequestBody UserDto userDto, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_CREATED);
    return userMapper.userToUserDto(userService.createUser(userDto));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PutMapping("/api/v1.0/users/{id}")
  public UserDto modifyUser(@RequestBody UserDto userDto, @PathVariable Long id, HttpServletResponse response) {
    if (!id.equals(userDto.getId())) {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      return null;
    }
    response.setStatus(HttpServletResponse.SC_ACCEPTED);
    return userMapper.userToUserDto(userService.modifyUser(userDto));
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @DeleteMapping("/api/v1.0/users/{id}")
  public void deleteUser(@PathVariable Long id, HttpServletResponse response) {
    userService.deleteUser(id);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything') OR ( hasAuthority('USER.Teams.read') AND @authorizationManager.isOwnUser(authentication, #id) )")
  @GetMapping("api/v1.0/users/{id}/teams")
  public List<TeamDto> getTeamsOfUser(@PathVariable Long id) {
    return userService.getOneWithTeams(id).getTeams()
      .stream().map(teamMapper::teamToTeamDto).collect(Collectors.toList());
  }


  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @PostMapping("/api/v1.0/users/{userId}/teams/{teamId}")
  public void addTeamToUser(@PathVariable Long userId, @PathVariable Long teamId, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_CREATED);
    userService.addUserToTeam(userId, teamId);
  }

  @PreAuthorize("hasAuthority('ADMIN.doAnything')")
  @DeleteMapping("/api/v1.0/users/{userId}/teams/{teamId}")
  public void removeUserFromTeam(@PathVariable Long userId, @PathVariable Long teamId, HttpServletResponse response) {
    userService.removeUserFromTeam(userId, teamId);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
