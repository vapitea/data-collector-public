package com.vapitea.datacollector.controller;


import com.vapitea.datacollector.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIT {

  public static final UserDto adminUser = UserDto.builder().name("Lewis Hamilton").password("12345").build();
  public static final UserDto operatorUser = UserDto.builder().name("Valtteri Bottas").password("password").build();
  public static final UserDto userUser = UserDto.builder().name("Max Verstappen").password("qwerty").build();
  public static final UserDto unAuthorizedUser = UserDto.builder().name("John Smith").password("picture1").build();

  @LocalServerPort
  protected int port;
  @Autowired
  protected TestRestTemplate restTemplate;


}
