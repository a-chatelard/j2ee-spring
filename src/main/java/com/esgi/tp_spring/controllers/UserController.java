package com.esgi.tp_spring.controllers;

import com.esgi.tp_spring.controllers.helpers.SecurityContextHelper;
import com.esgi.tp_spring.dto.requests.UserRequestDTO;
import com.esgi.tp_spring.dto.results.UserDetailsDTO;
import com.esgi.tp_spring.dto.results.TripDTO;
import com.esgi.tp_spring.dto.results.UserDTO;
import com.esgi.tp_spring.services.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityContextHelper contextHelper;

    @Operation(summary = "Création d'un utilisateur ou modification de l'utilisateur ayant l'username correspondant.")
    @RequestMapping(method = RequestMethod.PUT)
    public UserDetailsDTO createOrUpdate(@RequestBody @Valid UserRequestDTO user) {
        return userService.createOrUpdate(user);
    }

    @Operation(summary = "Récupération d'un utilisateur via son username.")
    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    public UserDetailsDTO getUser(@PathVariable(name = "username") String username) throws ResourceNotFoundException {
        return userService.getInfosById(username);
    }

    @Operation(summary = "Récupération de tous les utilisateurs avec pagination")
    @RequestMapping(method = RequestMethod.GET)
    public Page<UserDTO> getAllUsersPaged(Pageable pageable) {
        return userService.getAllPaged(pageable);
    }

    @Operation(summary = "Récupération de tous les utilisateurs avec filtre simple et pagination")
    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public Page<UserDTO> getAllUsersFilteredAndPaged(String filter, Pageable pageable) {
        return userService.getAllFilteredAndPaged(filter, pageable);
    }

    @Operation(summary = "Récupération des sorties d'un utilisateur")
    @RequestMapping(path = "/{username}/trips", method = RequestMethod.GET)
    public Page<TripDTO> getUserTripsParticipations(@PathVariable(name = "username") String username, Pageable pageable)
            throws ResourceNotFoundException {
        return userService.getTripsParticipationsPaged(username, pageable);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Suppression d'un utilisateur via son identifiant.")
    @RequestMapping(path ="/{username}", method = RequestMethod.DELETE)
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    public void deleteUser(@PathVariable(name = "username") String username) throws ResourceNotFoundException {
        userService.deleteById(username);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Mise à jour du mot de passe d'un utilisateur")
    @RequestMapping(path = "/update-password", method = RequestMethod.PUT)
    public void setUserPassword(@RequestParam (value = "username") String username,
                            @RequestParam(value = "password") String newPassword) throws IllegalAccessException {
        userService.setPassword(username, newPassword);
    }

    @Operation(summary = "Mise à jour du mot de passe de l'utilisateur connecté")
    @RequestMapping(path = "/password", method = RequestMethod.PUT)
    public void setOwnPassword(@RequestParam(value = "password") String newPassword) throws IllegalAccessException {
        var connectedUsername = contextHelper.GetUsernameFromContext(SecurityContextHolder.getContext());

        userService.setPassword(connectedUsername, newPassword);
    }
}