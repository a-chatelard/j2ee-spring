package com.esgi.tp_spring.services;

import com.esgi.tp_spring.dto.requests.TripRequestDTO;
import com.esgi.tp_spring.dto.results.TripDetailsDTO;
import com.esgi.tp_spring.dto.results.TripDTO;
import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.User;
import com.esgi.tp_spring.repositories.TripRepository;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripService.class);

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserService userService;

    public Trip createOrUpdate(TripRequestDTO tripDTO) throws ResourceNotFoundException {
        logger.info("Saving trip of name " + tripDTO.getName());
        Trip trip = new Trip();
        if (tripDTO.getId() != null) {
            trip = getById(tripDTO.getId());
        }

        User supervisor = userService.getById(tripDTO.getSupervisorUsername());

        trip.setName(tripDTO.getName());
        trip.setDescription(tripDTO.getDescription());
        trip.setDate(tripDTO.getDate());
        trip.setComplexity(tripDTO.getComplexity());
        trip.setDuration(tripDTO.getDuration());
        trip.setSupervisor(supervisor);
        trip.setLocation(tripDTO.getLocation());

        tripRepository.save(trip);
        logger.info("Trip of title " + trip.getName() + " saved with ID " + trip.getId());
        return trip;
    }

    public Trip getById(Long tripId) throws ResourceNotFoundException {
        var trip = tripRepository.findById(tripId);
        if (trip.isPresent()) {
            return trip.get();
        }
        logger.warn("Trip of id " + tripId + " not found.");
        throw new ResourceNotFoundException(Trip.class, tripId);
    }

    public TripDetailsDTO getInfosById(Long tripId) throws ResourceNotFoundException {
        var trip = getById(tripId);

        return new TripDetailsDTO(trip);
    }

    public Page<TripDTO> getAllPaged(Pageable pageable) {
        var result = tripRepository.findAll(pageable);
        return result.map(TripDTO::new);
    }

    public Page<TripDTO> getAllFilteredAndPaged(String filter, Pageable pageable) {
        var result = tripRepository.simpleSearch(filter, pageable);
        return result.map(TripDTO::new);
    }

    public Page<TripDTO> getAllInMonth(Integer month, Integer year, Pageable pageable) {
        var result = tripRepository.findByMonthAndYear(month, year, pageable);
        return result.map(TripDTO::new);
    }

    public Page<TripDTO> getAllWithoutParticipants(Pageable pageable) {
        var result = tripRepository.findByParticipantsEmpty(pageable);
        return result.map(TripDTO::new);
    }

    public void deleteById(Long tripId) throws ResourceNotFoundException {
        logger.info("Deleting trip of id " + tripId);
        var trip = tripRepository.findById(tripId);
        if (trip.isPresent()) {
            tripRepository.deleteById(tripId);
            logger.info("Trip of id " + tripId + " deleted.");
        }
        logger.error("Trip of id " + tripId + " not found.");
        throw new ResourceNotFoundException(Trip.class, tripId);
    }

    public TripDetailsDTO addParticipant(Long tripId, String username) throws ResourceNotFoundException {
        logger.info("Adding user with username " + username + " as participant to trip of id " + tripId);
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.addParticipant(user);

        tripRepository.save(trip);

        logger.info("User with username " + username + " added as participant to trip of id " + tripId);

        return new TripDetailsDTO(trip);
    }

    public TripDetailsDTO removeParticipant(Long tripId, String username) throws ResourceNotFoundException {
        logger.info("Removing user with username " + username + " from participating to trip of id " + tripId);
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.removeParticipant(user);

        tripRepository.save(trip);

        logger.info("User with username " + username + " removed from participation of trip of id " + tripId);

        return new TripDetailsDTO(trip);
    }

    public TripDetailsDTO updateSupervisor(Long tripId, String username) throws ResourceNotFoundException {
        logger.info("Updating supervisor of trip of id " + tripId + " to user with username " + username);
        var trip = getById(tripId);

        var user = userService.getById(username);

        trip.setSupervisor(user);

        tripRepository.save(trip);

        logger.info("Supervisor of trip of id " + tripId + " to user with username " + username + " updated.");

        return new TripDetailsDTO(trip);
    }
}
