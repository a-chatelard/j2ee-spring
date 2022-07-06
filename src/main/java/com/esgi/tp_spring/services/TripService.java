package com.esgi.tp_spring.services;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.repositories.TripRepository;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserService userService;

    public Trip createOrUpdate(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip getById(Long tripId) throws ResourceNotFoundException {
        var trip = tripRepository.findById(tripId);
        if (trip.isPresent()) {
            return trip.get();
        }
        throw new ResourceNotFoundException(Trip.class, tripId);
    }

    public Page<Trip> getAllPaged(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    public Page<Trip> getAllFilteredAndPaged(String filter, Pageable pageable) {
        return tripRepository.simpleSearch(filter, pageable);
    }

    public void deleteById(Long tripId) throws ResourceNotFoundException {
        var trip = tripRepository.findById(tripId);
        if (trip.isPresent()) {
            tripRepository.deleteById(tripId);
        }
        throw new ResourceNotFoundException(Trip.class, tripId);
    }

    public Trip addParticipant(Long tripId, String username) throws ResourceNotFoundException {
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.addParticipant(user);

        tripRepository.save(trip);

        return trip;
    }

    public Trip removeParticipant(Long tripId, String username) throws ResourceNotFoundException {
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.removeParticipant(user);

        tripRepository.save(trip);

        return trip;
    }

    public Trip updateSupervisor(Long tripId, String username) throws ResourceNotFoundException {
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.setSupervisor(user);

        tripRepository.save(trip);

        return trip;
    }
}
