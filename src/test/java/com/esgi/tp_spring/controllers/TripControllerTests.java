package com.esgi.tp_spring.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.services.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureJsonTesters
@WebMvcTest(TripController.class)
public class TripControllerTests {

    @MockBean
    private TripService tripService;

    @Autowired
    private JacksonTester<Trip> jsonTester;

    @Autowired
    private MockMvc mvc;

    @Test
    public void cannotAccessApiWithNoAuth() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/trips").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

}
