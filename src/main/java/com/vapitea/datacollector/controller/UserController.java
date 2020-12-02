package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/v1.0/users")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @PostMapping("api/v1.0/users")
    public User createUser(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return userService.createUser(user.getName(), user.getPassword()); //TODO Change this to DTO
    }

    @GetMapping("api/v1.0/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @GetMapping("api/v1.0/users/{id}/teams")
    public List<Team> getTeamsOfUser(@PathVariable Long id) {
        return userService.getOneWithTeams(id).getTeams();
    }

    @PutMapping("/api/v1.0/users/{id}")
    public User modifyUser(@RequestBody User user, @PathVariable Long id, HttpServletResponse response) {
        if (!id.equals(user.getId())) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }
        return userService.modifyUser(id, user);
    }

    @DeleteMapping("/api/v1.0/users/{id}")
    public void deleteUser(@PathVariable Long id, HttpServletResponse response) {

        userService.deleteUser(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PostMapping("/api/v1.0/users/{id}/teams")
    public User addTeamToUser(@PathVariable Long id, @RequestBody Team team, HttpServletResponse response) {
        User user = User.builder().id(id).build();
        return userService.addUserToTeam(user, team);
    }

    @DeleteMapping("/api/v1.0/users/{userId}/teams/{teamId}")
    public void removeUserFromTeam(@PathVariable Long userId, @PathVariable Long teamId, HttpServletResponse response) {
        userService.removeUserFromTeam(userId, teamId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
