package com.esgi.tp_spring.services;

import com.esgi.tp_spring.dto.results.UserUsernameDTO;
import com.esgi.tp_spring.helpers.DTOService;
import com.esgi.tp_spring.helpers.UserHelper;
import com.esgi.tp_spring.services.TripService;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TripServiceTests {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private DTOService dtoService;

    @Test
    public void testAddTrip() throws ResourceNotFoundException {
        // Arrange
        var user = userHelper.createUser("testUser");
        var tripDTO = dtoService.GenerateTripDTO(user.getUsername());

        // Act
        var trip = tripService.createOrUpdate(tripDTO);

        // Assert
        assertThat(trip.getId()).isNotNull();

        // Clean
        tripService.deleteById(trip.getId());
        userHelper.removeUser(user.getUsername());
    }

    @Test
    public void testUpdateTrip() throws ResourceNotFoundException {
        // Arrange
        var user = userHelper.createUser("testUser");
        var tripDTO = dtoService.GenerateTripDTO(user.getUsername());
        var firstSavedTrip = tripService.createOrUpdate(tripDTO);

        // Act
        var updatedName = "Updated Name";
        tripDTO.setId(firstSavedTrip.getId());
        tripDTO.setName(updatedName);
        var secondSavedTrip = tripService.createOrUpdate(tripDTO);

        // Assert
        assertThat(secondSavedTrip.getName()).isEqualTo(updatedName);
        assertThat(firstSavedTrip.getId()).isEqualTo(secondSavedTrip.getId());

        // Clean
        tripService.deleteById(firstSavedTrip.getId());
        userHelper.removeUser(user.getUsername());
    }

    @Test
    public void testDeleteTrip() throws ResourceNotFoundException {
        // Arrange
        var user = userHelper.createUser("testUser");
        var tripDTO = dtoService.GenerateTripDTO(user.getUsername());
        var trip = tripService.createOrUpdate(tripDTO);

        // Act
        tripService.deleteById(trip.getId());

        // Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
           tripService.getById(trip.getId());
        });

        assertThat(exception.getMessage()).isEqualTo("Trip " + trip.getId() + " not found.");

        // Clean
        userHelper.removeUser(user.getUsername());
    }

    @Test
    public void testAddParticipant() throws ResourceNotFoundException {
        // Arrange
        var supervisorUser = userHelper.createUser("supervisorUser");
        var tripDTO = dtoService.GenerateTripDTO(supervisorUser.getUsername());
        var trip = tripService.createOrUpdate(tripDTO);

        var participantUser = userHelper.createUser("participantUser");

        // Act
        var result = tripService.addParticipant(trip.getId(), participantUser.getUsername());

        // Assert
        var participantsIds = result.getParticipants().stream().map(UserUsernameDTO::getUsername).toList();
        assertTrue(participantsIds.contains(participantUser.getUsername()));
    }

    @Test
    public void testRemoveParticipant() throws ResourceNotFoundException {
        // Arrange
        var supervisorUser = userHelper.createUser("supervisorUser");
        var tripDTO = dtoService.GenerateTripDTO(supervisorUser.getUsername());
        var trip = tripService.createOrUpdate(tripDTO);
        var participantUser = userHelper.createUser("participantUser");
        tripService.addParticipant(trip.getId(), participantUser.getUsername());

        // Act
        var result = tripService.removeParticipant(trip.getId(), participantUser.getUsername());

        // Assert
        var participantsIds = result.getParticipants().stream().map(UserUsernameDTO::getUsername).toList();
        assertFalse(participantsIds.contains(participantUser.getUsername()));
    }
}
