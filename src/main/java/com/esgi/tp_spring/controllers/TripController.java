package com.esgi.tp_spring.controllers;

import com.esgi.tp_spring.controllers.helpers.SecurityContextHelper;
import com.esgi.tp_spring.dto.requests.TripRequestDTO;
import com.esgi.tp_spring.dto.results.TripDetailsDTO;
import com.esgi.tp_spring.dto.results.TripDTO;
import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.services.TripService;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private SecurityContextHelper contextHelper;

    @Operation(summary = "Création d'une sortie ou modification d'une sortie existante")
    @RequestMapping(method = RequestMethod.PUT)
    public Trip createOrUpdateTrip(@RequestBody @Valid TripRequestDTO trip) throws ResourceNotFoundException {
        return tripService.createOrUpdate(trip);
    }

    @Operation(summary = "Récupération d'une sortie à partir de son identifiant.")
    @RequestMapping(path = "/{tripId}", method = RequestMethod.GET)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public TripDetailsDTO getTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        return tripService.getInfosById(tripId);
    }

    @Operation(summary = "Récupération de toutes les sorties avec pagination")
    @RequestMapping(method = RequestMethod.GET)
    public Page<TripDTO> getAllTripsPaged(Pageable pageable) {
        return tripService.getAllPaged(pageable);
    }

    @Operation(summary = "Récupération de toutes les sorties avec filtre simple et pagination")
    @RequestMapping(path = "/filter",method = RequestMethod.GET)
    public Page<TripDTO> getAllTripsFilteredAndPaged(String filter, Pageable pageable) {
        return tripService.getAllFilteredAndPaged(filter, pageable);
    }

    @Operation(summary = "Récupération des sorties dans un mois donné.")
    @RequestMapping(path = "/month",method = RequestMethod.GET)
    public Page<TripDTO> getAllTripsInMonth(Integer month, Integer year, Pageable pageable) {
        return tripService.getAllInMonth(month, year, pageable);
    }

    @Operation(summary = "Récupération des sorties sans participant.")
    @RequestMapping(path = "/empty",method = RequestMethod.GET)
    public Page<TripDTO> getAllTripsWithoutParticipants(Pageable pageable) {
        return tripService.getAllWithoutParticipants(pageable);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Suppression d'une sortie à partir de son identifiant.")
    @RequestMapping(path = "/{tripId}", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public void deleteTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        tripService.deleteById(tripId);
    }

    @Operation(summary = "Ajout de l'utilisateur connecté à une sortie via l'identifiant de la sortie.")
    @RequestMapping(path = "/{tripId}/participant", method = RequestMethod.PATCH)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé(e).")
    public TripDetailsDTO addConnectedUserToTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        var connectedUsername = contextHelper.GetUsernameFromContext(SecurityContextHolder.getContext());

        return tripService.addParticipant(tripId, connectedUsername);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Ajout d'un participant à une sortie via l'identifiant de la sortie et de l'utilisateur.")
    @RequestMapping(path = "/{tripId}/participant/{userId}", method = RequestMethod.PATCH)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public TripDetailsDTO addParticipantToTrip(@PathVariable(name = "tripId") Long tripId,
                                               @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.addParticipant(tripId, userId);
    }

    @Operation(summary = "Suppression de l'utilisateur connecté à une sortie via l'identifiant de la sortie.")
    @RequestMapping(path = "/{tripId}/participant", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé(e).")
    public TripDetailsDTO removeConnectedUserFromTrip(@PathVariable(name = "tripId") Long tripId) throws ResourceNotFoundException {
        var connectedUsername = contextHelper.GetUsernameFromContext(SecurityContextHolder.getContext());

        return tripService.removeParticipant(tripId, connectedUsername);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Suppression d'un participant à une sortie via l'identifiant de la sortie et de l'utilisateur.")
    @RequestMapping(path = "/{tripId}/participant/{userId}", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public TripDetailsDTO removeParticipantToTrip(@PathVariable(name = "tripId") Long tripId,
                                                  @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.removeParticipant(tripId, userId);
    }

    @Operation(summary = "Modification du superviseur de la sortie via l'identifiant de la sortie et de l'utilisateur")
    @RequestMapping(path = "/{tripId}/supervisor/{userId}", method = RequestMethod.PATCH)
    @ApiResponse(responseCode = "404", description = "Utilisateur ou sortie non trouvé")
    public TripDetailsDTO updateSupervisor(@PathVariable(name = "tripId") Long tripId,
                                           @PathVariable(name = "userId") String userId) throws ResourceNotFoundException {
        return tripService.updateSupervisor(tripId, userId);
    }
}