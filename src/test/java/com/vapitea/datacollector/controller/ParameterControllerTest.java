package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Measurement;
import com.vapitea.datacollector.model.Parameter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParameterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getParameter() {
        //Arrange
        Parameter expectedParameter = Parameter.builder()
                .id(3L)
                .name("wind")
                .unit("km/h")
                .description(null)
                .build();

        //Act
        Parameter parameter = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/3", Parameter.class);

        //Assert
        assertEquals(expectedParameter, parameter);
        assertEquals(expectedParameter.getName(), parameter.getName());
        assertEquals(expectedParameter.getUnit(), parameter.getUnit());
        assertEquals(expectedParameter.getDescription(), parameter.getDescription());
    }

    @Test
    @DirtiesContext
    void deleteParameter() {
        //Arrange

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/parameters/3");

        //Assert
        Parameter parameter = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/3", Parameter.class);

        assertNull(parameter);
    }

    @Test
    @DirtiesContext
    void modifyParameter() {
        //Arrange
        Parameter expectedParameter = Parameter.builder()
                .id(3L)
                .name("wind modified")
                .unit("km/h modified")
                .description("this is also different")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> entity = new HttpEntity<Parameter>(expectedParameter, headers);

        //Act

        restTemplate.put("http://localhost:" + port + "/api/v1.0/parameters/3", entity);

        //Assert
        Parameter actualParameter = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/3", Parameter.class);

        assertEquals(expectedParameter, actualParameter);
        assertEquals(expectedParameter.getName(), actualParameter.getName());
        assertEquals(expectedParameter.getUnit(), actualParameter.getUnit());
        assertEquals(expectedParameter.getDescription(), actualParameter.getDescription());
    }

    @Test
    @DirtiesContext
    void createNewParameter() {
        //Arrange
        Parameter expectedParameter = Parameter.builder()
                .id(null)
                .name("new parameter")
                .unit("new unit")
                .description(null)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> entity = new HttpEntity<Parameter>(expectedParameter, headers);

        //Act

        Parameter actualParameter = restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/dataSources/4/parameters", entity, Parameter.class);

        //Assert
        assertNotNull(actualParameter.getId());
        assertEquals(expectedParameter.getName(), actualParameter.getName());
        assertEquals(expectedParameter.getUnit(), actualParameter.getUnit());
        assertEquals(expectedParameter.getDescription(), actualParameter.getDescription());
    }


}