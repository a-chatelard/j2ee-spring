package com.esgi.tp_spring.controllers;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.services.TripService;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Operation(summary = "Création d'une sortie ou modification d'une sortie existante")
    @RequestMapping(method = RequestMethod.PUT)
    public Trip createOrUpdateTrip(@RequestBody @Valid Trip trip) {
        return tripService.createOrUpdate(trip);
    }

    @Operation(summary = "Récupération d'une sortie à partir de son identifiant.")
    @RequestMapping(path = "/{tripId}", method = RequestMethod.GET)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public Trip getTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        return tripService.getById(tripId);
    }

    @Operation(summary = "Récupération de toutes les sorties avec pagination")
    @RequestMapping(method = RequestMethod.GET)
    public Page<Trip> getAllPaged(Pageable pageable) {
        return tripService.getAllPaged(pageable);
    }

    @Operation(summary = "R&cupération de toutes les sorties avec filtre simple et pagination")
    @RequestMapping(path = "/filtered",method = RequestMethod.GET)
    public Page<Trip> getAllFilteredAndPaged(String filter, Pageable pageable) {
        return tripService.getAllFilteredAndPaged(filter, pageable);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Suppression d'une sortie à partir de son identifiant.")
    @RequestMapping(path = "/{tripId}", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public void deleteTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        tripService.deleteById(tripId);
    }

    @Operation(summary = "Ajout d'un participant à une sortie via l'identifiant de la sortie et de l'utilisateur.")
    @RequestMapping(path = "/{tripId}/participant/{userId}", method = RequestMethod.PATCH)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public Trip addParticipantToTrip(@PathVariable(name = "tripId") Long tripId,
                               @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.addParticipant(tripId, userId);
    }

    @Operation(summary = "Suppression d'un participant à une sortie via l'identifiant de la sortie et de l'utilisateur.")
    @RequestMapping(path = "/{tripId}/participant/{userId}", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public Trip removeParticipantToTrip(@PathVariable(name = "tripId") Long tripId,
                                  @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.removeParticipant(tripId, userId);
    }

    @Operation(summary = "Modification du superviseur de la sortie via l'identifiant de la sortie et de l'utilisateur")
    @RequestMapping(path = "/{tripId}/supervisor/{userId}", method = RequestMethod.PATCH)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public Trip updateSupervisor(@PathVariable(name = "tripId") Long tripId,
                                 @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.updateSupervisor(tripId, userId);
    }
}