package com.esgi.tp_spring;

import com.esgi.tp_spring.dto.requests.TripRequestDTO;
import com.esgi.tp_spring.dto.results.TripDTO;
import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.User;
import com.esgi.tp_spring.repositories.UserRepository;
import com.esgi.tp_spring.services.TripService;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TripServiceTests {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddTrip() throws ResourceNotFoundException {
        // Arrange
        var user = new User();
        user.setUsername("testUser");
        userRepository.save(user);

        var tripDTO = new TripRequestDTO();
        tripDTO.setName("Sortie test");
        tripDTO.setDuration(10);
        tripDTO.setSupervisorUsername(user.getUsername());


        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 10);
        tripDTO.setDate(c.getTime());


        // Act
        Trip trip = tripService.createOrUpdate(tripDTO);

        // Assert
        assertThat(trip.getId()).isNotNull();

        // Clean
        tripService.deleteById(trip.getId());
    }

    @Test
    public void testUpdateSortie() {
// Test case
        TripDTO
        sortie.setName("Sortie test");
        Sortie savedSortie = sortieService.createOrUpdate(sortie);
        savedSortie.setName("Sortie test 2");
        Sortie savedSortie2 = sortieService.createOrUpdate(savedSortie);
// Asserts
        assertThat(savedSortie2.getName()).isEqualTo("Sortie test 2");
        assertThat(savedSortie.getId()).isEqualTo(savedSortie2.getId());
// Clean
        sortieService.deleteSortieById(savedSortie.getId());
    }
}
