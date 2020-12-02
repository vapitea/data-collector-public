package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.Team;
import com.vapitea.datacollector.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllUsers() {
        //Arrange

        //Act
        User[] users = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users", User[].class);

        //Assert
        assertEquals(4, users.length);
        assertEquals(1, users[0].getId());
        assertEquals("Lewis Hamilton", users[0].getName());
        assertEquals("12345", users[0].getPassword());
    }

    @Test
    @DirtiesContext
    void createUser() {
        //Arrange
        String userJson = "{\"name\": \"George Russel\", \"password\": \"Williams\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

        //Act
        User user = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/users", entity, User.class);

        //Assert
        assertEquals("George Russel", user.getName());
        assertEquals("Williams", user.getPassword());
        assertNotNull(user.getId());
    }

    @Test
    void getSingleUser() {
        //Arrange

        //Act
        User user = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users/2", User.class);

        //Assert
        assertEquals(2, user.getId());
        assertEquals("Valtteri Bottas", user.getName());
        assertEquals("password", user.getPassword());
    }

    @Test
    @DirtiesContext
    void modifyUser() {
        //Arrange
        String userJson = "{\"id\":2, \"name\": \"Modified  Bottas\", \"password\": \"PASSWORD\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

        //Act
        restTemplate.put("http://localhost:" + port + "/api/v1.0/users/2", entity);

        //Assert
        User user = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users/2", User.class);
        assertEquals(2, user.getId());
        assertEquals("Modified  Bottas", user.getName());
        assertEquals("PASSWORD", user.getPassword());
    }

    @Test
    @DirtiesContext
    void deleteUser() {
        //Arrange

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/users/2");

        //Assert
        User[] users = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users", User[].class);
        assertEquals(3, users.length);
    }

    @Test
    @DirtiesContext
    void addUserToTeam() {
        //Arrange
        Team team = new Team(6L, "Renault Engine supplier", null, null, null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Team> entity = new HttpEntity<Team>(team, headers);

        //Act
        User user = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/users/3/teams", entity, User.class);
        Team[] teams = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users/3/teams", Team[].class);

        //Assert
        assertThat(teams).contains(team);
    }

    @Test
    void getTeamsOfUser() {
        //Arrange
        Team team1 = new Team(1L, "Mercedes", null, null, null);
        Team team2 = new Team(5L, "Mercedes Engine supplier", null, null, null);

        //Act
        Team[] teams = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/users/1/teams", Team[].class);

        //Assert

        assertEquals(2, teams.length);
        assertThat(teams).contains(team1, team2);
    }

}