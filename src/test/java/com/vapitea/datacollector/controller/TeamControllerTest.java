package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import javax.xml.crypto.Data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamControllerTest{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllTeams() {
        //Arrange

        //Act
        Team[] teams = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams", Team[].class);

        //Assert
        assertEquals(8, teams.length);
        assertEquals(1, teams[0].getId());
        assertEquals("Mercedes", teams[0].getName());
    }

    @Test
    @DirtiesContext
    void removeTeamFromUser() {
        //Arrange
        Team team1 = new Team(1L, "Mercedes", null, null, null);
        Team team2 = new Team(5L, "Mercedes Engine supplier", null, null, null);

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/users/1/teams/5");

        //Assert
        Team[] teams = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users/1/teams", Team[].class);
        assertEquals(1, teams.length);
        assertThat(teams).contains(team1).doesNotContain(team2);
    }

    @Test
    @DirtiesContext
    void removeUserFromTeam() {
        //Arrange
        User user0 = User.builder().id(1L).build();
        User user1 = User.builder().id(2L).build();

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/teams/1/users/2");

        //Assert
        User[] users = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/1/users", User[].class);
        assertThat(users)
                .contains(user0)
                .doesNotContain(user1)
                .hasSize(1);
    }

    @Test
    void getSingleTeam() {
        //Arrange

        //Act
        Team team = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/4", Team.class);

        //Assert
        assertEquals(4, team.getId());
        assertEquals("Ferrari", team.getName());
    }

    @Test
    @DirtiesContext
    void deleteTeam() {
        //Arrange
        Team team = Team.builder().id(3L).build();

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/teams/3");

        //Assert
        Team[] teams = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams", Team[].class);

        assertEquals(7, teams.length);
        assertThat(teams).doesNotContain(team);
    }

    @Test
    @DirtiesContext
    void modifyTeam() {
        //Arrange
        String teamJson = "{\"id\":7, \"name\": \"Honda Engine supplier\", \"description\": \"Left F1 in 2021\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(teamJson, headers);

        //Act
        restTemplate.put("http://localhost:" + port + "/api/v1.0/teams/7", entity);

        //Assert
        Team team = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/7", Team.class);
        assertEquals(7, team.getId());
        assertEquals("Honda Engine supplier", team.getName());
        assertEquals("Left F1 in 2021", team.getDescription());
    }

    @Test
    void getUsersOfTeam() {
        //Arrange
        User user0 = User.builder().id(1L).build();
        User user1 = User.builder().id(2L).build();

        //Act
        User[] users = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/1/users", User[].class);

        //Assert
        assertThat(users)
                .contains(user0, user1)
                .hasSize(2);
    }

    @Test
    @DirtiesContext
    void addUserToTeam() {
        //Arrange
        User user = User.builder().id(4L).name("Lando Norris").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        //Act
        Team team = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/teams/4/users", entity, Team.class);

        //Assert
        User[] users = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/4/users", User[].class);
        assertThat(users)
                .contains(user)
                .hasSize(1);
    }

    @Test
    void getDataSourcesOfTeam() {
        //Arrange
        DataSource dataSource0 = DataSource.builder().id(3L).build();
        DataSource dataSource1 = DataSource.builder().id(4L).build();


        //Act
        DataSource[] dataSources = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/2/dataSources", DataSource[].class);

        //Assert
        assertThat(dataSources)
                .contains(dataSource0, dataSource1)
                .hasSize(2);
    }

    @Test
    @DirtiesContext
    void deleteDataSourceOfTeam() {
        //Arrange
        DataSource dataSource0 = DataSource.builder().id(3L).build();
        DataSource dataSource1 = DataSource.builder().id(4L).build();

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/teams/2/dataSources/3");

        //Assert
        DataSource[] dataSources = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/2/dataSources", DataSource[].class);
        assertThat(dataSources)
                .contains(dataSource1)
                .doesNotContain(dataSource0)
                .hasSize(1);
    }

    @Test
    @DirtiesContext
    void createNewTeam() {
        //Arrange
        Team expectedTeam = Team.builder()
                .id(null)
                .name("New Team")
                .description("The newest team")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Team> entity = new HttpEntity<Team>(expectedTeam, headers);

        //Act
        Team actualTeam = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/teams", entity, Team.class);

        //Assert
        assertNotNull(actualTeam);
        assertEquals(expectedTeam.getName(), actualTeam.getName());
        assertEquals(expectedTeam.getDescription(), actualTeam.getDescription());

    }

    @Test
    @DirtiesContext
    void createNewDataSource() {
        //Arrange
        DataSource expected = DataSource.builder()
                .id(null)
                .uuid("new uuid")
                .name("new Node")
                .location("Szeged")
                .description("")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DataSource> entity = new HttpEntity<DataSource>(expected, headers);

        //Act
        DataSource dataSource = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/teams/2/dataSources", entity, DataSource.class);


        //Assert
        DataSource[] dataSources = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/teams/2/dataSources", DataSource[].class);
        assertThat(dataSources).contains(dataSource);

    }
}
