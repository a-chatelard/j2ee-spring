package com.esgi.tp_spring.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void cannotAccessApiWithNoAuth() {
        // when
        ResponseEntity<Page> response = this.restTemplate.getForEntity("/trips", Page.class);
        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
