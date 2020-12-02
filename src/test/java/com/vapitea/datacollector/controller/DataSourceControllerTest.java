package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.model.DataSource;
import com.vapitea.datacollector.model.Parameter;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataSourceControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getDataSource() {
        //Arrange
        DataSource expected = DataSource.builder()
                .id(3L)
                .uuid("f21a46f3-7065-4d14-9bf4-83ce51ad8dc7")
                .name("Node2")
                .location("Visegrad")
                .description("")
                .build();
        //Act
        DataSource actual = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/3", DataSource.class);

        //Assert
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @DirtiesContext
    void modifyDataSource() {
        //Arrange
        DataSource expected = DataSource.builder()
                .id(3L)
                .uuid("modified uuid")
                .name("Node2 modified")
                .location("Visegrad")
                .description("")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DataSource> entity = new HttpEntity<DataSource>(expected, headers);

        //Act
        restTemplate.put("http://localhost:" + port + "/api/v1.0/dataSources/3", entity, DataSource.class);

        //Assert
        DataSource actual = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/3", DataSource.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @DirtiesContext
    void deleteDataSource() {
        //Arrange
        DataSource expected = DataSource.builder().id(3L).build();

        //Act
        DataSource firstGet = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/3", DataSource.class);
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/dataSources/3");
        DataSource secondGet = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/3", DataSource.class);

        //Assert
        assertEquals(expected, firstGet);
        assertNull(secondGet);
    }

    @Test
    void getParametersOfDataSource() {
        //Arrange
        Parameter parameter0 = Parameter.builder().id(5L).build();
        Parameter parameter1 = Parameter.builder().id(6L).build();

        //Act
        Parameter[] parameters = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/2/parameters", Parameter[].class);

        //Assert
        assertThat(parameters).containsExactlyInAnyOrder(parameter0, parameter1);
    }

    @Test
    @DirtiesContext
    void deleteParameterOfDataSource() {
        //Arrange
        Parameter parameter = Parameter.builder().id(6L).build();

        //Act
        restTemplate.delete("http://localhost:" + port + "/api/v1.0/dataSources/2/parameters/6");

        //Assert
        Parameter[] parameters = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/dataSources/2/parameters", Parameter[].class);
        assertThat(parameters).doesNotContain(parameter);
    }


}