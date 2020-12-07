package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.TeamDto;
import com.vapitea.datacollector.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest extends BaseIT {


  @Test
  void getAllUsersAdmin() {
    //Arrange

    //Act
    ResponseEntity<UserDto[]> responseEntity = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users", HttpMethod.GET, null, UserDto[].class);

    //Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertThat(responseEntity.getBody()).hasSize(4);
  }

  @Test
  void getAllUsersNotAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(operatorUser.getName(), operatorUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users", HttpMethod.GET, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  void getAllUsersUnAuthorizedUser() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(unAuthorizedUser.getName(), unAuthorizedUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users", HttpMethod.GET, null, String.class);

    //Assert
    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }


  @Test
  void getSingleOtherUserAdmin() {
    //Arrange

    //Act
    ResponseEntity<UserDto> responseEntity = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2", HttpMethod.GET, null, UserDto.class);

    //Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertThat(responseEntity.getBody().getId()).isEqualTo(2L);
    assertThat(responseEntity.getBody().getName()).isEqualTo("Valtteri Bottas");
  }

  @Test
  void getSingleOtherUserNonAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(userUser.getName(), userUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2", HttpMethod.GET, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  void getSingleOwnUserUser() {
    //Arrange

    //Act
    ResponseEntity<UserDto> responseEntity = restTemplate
      .withBasicAuth(userUser.getName(), userUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/3", HttpMethod.GET, null, UserDto.class);

    //Assert
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertThat(responseEntity.getBody().getId()).isEqualTo(3L);
    assertThat(responseEntity.getBody().getName()).isEqualTo("Max Verstappen");
  }

  @Test
  void getSingleOtherUserUser() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(userUser.getName(), userUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2", HttpMethod.GET, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  @DirtiesContext
  void createUserAdmin() {
    //Arrange
    String userJson = "{\"name\": \"George Russel\", \"password\": \"Williams\", \"dType\": \"User\"}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

    //Act
    ResponseEntity<UserDto> userDto = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users", HttpMethod.POST, entity, UserDto.class);

    //Assert
    assertEquals(HttpStatus.CREATED, userDto.getStatusCode());
    assertEquals("George Russel", userDto.getBody().getName());
    assertNotNull(userDto.getBody().getId());
  }

  @Test
  void createUserNotAdmin() {
    //Arrange
    String userJson = "{\"name\": \"George Russel\", \"password\": \"Williams\", \"dType\": \"User\"}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(operatorUser.getName(), operatorUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users", HttpMethod.POST, entity, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  @DirtiesContext
  void modifyUserAdmin() {
    //Arrange
    String userJson = "{\"id\":2, \"name\": \"Modified  Bottas\", \"password\": \"PASSWORD\", \"dType\": \"Operator\"}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

    //Act
    ResponseEntity<UserDto> userDto = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2", HttpMethod.PUT, entity, UserDto.class);

    //Assert
    assertEquals(HttpStatus.ACCEPTED, userDto.getStatusCode());
    assertEquals(2, userDto.getBody().getId());
    assertEquals("Modified  Bottas", userDto.getBody().getName());
  }

  @Test
  @DirtiesContext
  void deleteUserAdmin() {
    //Arrange

    //Act
    restTemplate.withBasicAuth(adminUser.getName(), adminUser.getPassword()).delete("http://localhost:" + port + "/api/v1.0/users/2");

    //Assert
    UserDto[] userDtos = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .getForObject("http://localhost:" + port + "/api/v1.0/users", UserDto[].class);
    assertEquals(3, userDtos.length);
  }

  @Test
  void deleteUserNonAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(operatorUser.getName(), operatorUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2", HttpMethod.DELETE, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  @DirtiesContext
  void addUserToTeamAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2/teams/3", HttpMethod.POST, null, String.class);

    //Assert
    ResponseEntity<TeamDto[]> teams = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2/teams", HttpMethod.GET, null, TeamDto[].class);

    assertThat(teams.getBody()).hasSize(3);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  void addUserToTeamNotAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(operatorUser.getName(), operatorUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/2/teams/3", HttpMethod.POST, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  void getTeamsOfUserAdmin() {
    //Arrange

    //Act
    TeamDto[] teams = restTemplate
      .withBasicAuth(adminUser.getName(), adminUser.getPassword())
      .getForObject("http://localhost:" + port + "/api/v1.0/users/2/teams", TeamDto[].class);

    //Assert
    assertEquals(2, teams.length);
  }

  @Test
  void getTeamsOfUserNotAdmin() {
    //Arrange

    //Act
    ResponseEntity<String> responseEntity = restTemplate
      .withBasicAuth(operatorUser.getName(), operatorUser.getPassword())
      .exchange("http://localhost:" + port + "/api/v1.0/users/1/teams", HttpMethod.GET, null, String.class);

    //Assert
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }


  @Test
  void getTeamsOfUserOwnUser() {
    //Arrange

    //Act
    TeamDto[] teams = restTemplate
      .withBasicAuth(userUser.getName(), userUser.getPassword())
      .getForObject("http://localhost:" + port + "/api/v1.0/users/3/teams", TeamDto[].class);

    //Assert
    assertEquals(1, teams.length);
  }

}
