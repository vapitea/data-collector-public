package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import com.vapitea.datacollector.service.DataSourceService;
import com.vapitea.datacollector.service.TeamService;
import com.vapitea.datacollector.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final DataSourceService dataSourceService;

    public TeamController(TeamService teamService, UserService userService, DataSourceService dataSourceService) {
        this.teamService = teamService;
        this.userService = userService;
        this.dataSourceService = dataSourceService;
    }

    @GetMapping("api/v1.0/teams")
    public List<Team> getTeams() {
        return teamService.getAll();
    }

    @GetMapping("api/v1.0/teams/{id}")
    public Team getTeam(@PathVariable Long id) {
        return teamService.getOne(id);
    }

    @DeleteMapping("api/v1.0/teams/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    @PutMapping("api/v1.0/teams/{id}")
    public Team modifyTeam(@PathVariable Long id, @RequestBody Team team, HttpServletResponse response) {
        if (!id.equals(team.getId())) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return null;
        }
        return teamService.modifyTeam(team);

    }

    @GetMapping("api/v1.0/teams/{id}/users")
    public List<User> getUsersOfTeam(@PathVariable Long id) {
        return teamService.getTeamWithUsers(id).getUsers();
    }

    @DeleteMapping("api/v1.0/teams/{teamId}/users/{userId}")
    public void removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        userService.removeUserFromTeam(userId, teamId);
    }

    @PostMapping("/api/v1.0/teams/{id}/users")
    public Team addTeamToUser(@PathVariable Long id, @RequestBody User user, HttpServletResponse response) {
        return teamService.addUserToTeam(user, id);
    }

    @GetMapping("/api/v1.0/teams/{id}/dataSources")
    public List<DataSource> getDataSourcesOfTeam(@PathVariable Long id) {
        return teamService.getOneWithDataSources(id).getDataSources();
    }

    @DeleteMapping("/api/v1.0/teams/{teamId}/dataSources/{dataSourceId}")
    public void deleteDataSourceOfTeam(@PathVariable Long teamId, @PathVariable Long dataSourceId, HttpServletResponse response) {
        dataSourceService.deleteDataSource(dataSourceId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PostMapping("/api/v1.0/teams")
    public Team createNewTeam(@RequestBody Team team, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return teamService.createTeam(team.getName(), team.getDescription());
    }

    @PostMapping("/api/v1.0/teams/{id}/dataSources")
    public DataSource createNewDataSource(@PathVariable Long id, @RequestBody DataSource dataSource, HttpServletResponse response) {
        Team team = Team.builder().id(id).build();
        response.setStatus(HttpServletResponse.SC_CREATED);
        return dataSourceService.createDataSource(dataSource, team);

    }

}


