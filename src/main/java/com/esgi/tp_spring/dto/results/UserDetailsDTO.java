package com.esgi.tp_spring.dto.results;

import com.esgi.tp_spring.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDTO extends UserDTO {

    private List<AddressDTO> addresses = new ArrayList<>();

    private List<TripDTO> tripsParticipations = new ArrayList<>();

    private List<TripDTO> tripsSupervisions = new ArrayList<>();

    public UserDetailsDTO() {}

    public UserDetailsDTO(User user) {
        super(user);
        this.addresses = user.getAddresses().stream().map(AddressDTO::new).toList();
        this.tripsParticipations = user.getTripsParticipations().stream().map(TripDTO::new).toList();
        this.tripsSupervisions = user.getTripsSupervisions().stream().map(TripDTO::new).toList();
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<TripDTO> getTripsParticipations() {
        return tripsParticipations;
    }

    public void setTripsParticipations(List<TripDTO> tripsParticipations) {
        this.tripsParticipations = tripsParticipations;
    }

    public List<TripDTO> getTripsSupervisions() {
        return tripsSupervisions;
    }

    public void setTripsSupervisions(List<TripDTO> tripsSupervisions) {
        this.tripsSupervisions = tripsSupervisions;
    }
}
