package com.esgi.tp_spring.controllers;

import com.esgi.tp_spring.dto.results.TripDetailsDTO;
import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.helpers.TripHelper;
import com.esgi.tp_spring.helpers.UserHelper;
import com.esgi.tp_spring.services.TripService;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TripControllerTests {

    private MockMvc mvc;

    @Autowired
    private TripController tripController;

    @MockBean
    private TripService tripService;

    @Autowired
    private TripHelper tripHelper;
    @Autowired
    private UserHelper userHelper;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(tripController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        var user = userHelper.createUser("userTest");
        var trip = tripHelper.createTrip(user);
        BDDMockito
                .given(tripService.getInfosById(1L))
                .willReturn(new TripDetailsDTO(trip));
        // when
        MockHttpServletResponse response = mvc
                .perform(MockMvcRequestBuilders.get("/trips/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                objectMapper.writeValueAsString(new TripDetailsDTO(trip))
        );
    }
}
