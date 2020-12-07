package com.vapitea.datacollector.controller;

import com.vapitea.datacollector.dto.MeasurementMessage;
import com.vapitea.datacollector.model.Measurement;
import com.vapitea.datacollector.model.Parameter;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MeasurementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DirtiesContext
    void postMeasurements() {
        //Arrange
        MeasurementMessage measurementMessage = new MeasurementMessage();
        measurementMessage.setUuid("9f2dce02-7aa9-4bd0-8571-a979a437f2bd"); //UID of a DataSource (id=2)
        Map<String, Double> payload = new HashMap<>();
        payload.put("temperature", 23.34);
        payload.put("humidity", 69.67);
        measurementMessage.setPayload(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MeasurementMessage> entity = new HttpEntity<MeasurementMessage>(measurementMessage, headers);


        //Act
        restTemplate.postForObject("http://localhost:" + port + "/api/v1.0/measurements", entity, String.class);


        //Assert
        Measurement[] measurements0 = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/5/measurements", Measurement[].class);
        Measurement[] measurements1 = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/6/measurements", Measurement[].class);

        assertThat(measurements0).hasSize(1);
        assertThat(measurements1).hasSize(2);
    }

    @Test
    void getMeasurements() {
        //Arrange
        Measurement measurement0 = Measurement.builder().id(4L).build();
        Measurement measurement1 = Measurement.builder().id(5L).build();
        Measurement measurement2 = Measurement.builder().id(6L).build();

        //Act
        Measurement[] measurements = restTemplate.getForObject("http://localhost:" + port + "/api/v1.0/parameters/2/measurements", Measurement[].class);

        //Assert
        assertThat(measurements).containsExactlyInAnyOrder(measurement0, measurement1, measurement2);
    }
}
