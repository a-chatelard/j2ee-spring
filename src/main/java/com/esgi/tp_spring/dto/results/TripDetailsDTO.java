package com.esgi.tp_spring.dto.results;

import com.esgi.tp_spring.entities.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripDetailsDTO extends TripDTO {

    private List<UserUsernameDTO> participants = new ArrayList<>();

    private UserUsernameDTO supervisor;

    public TripDetailsDTO() {}

    public TripDetailsDTO(Trip trip) {
        super(trip);
        this.participants = trip.getParticipants().stream().map(UserUsernameDTO::new).toList();
        this.supervisor = new UserUsernameDTO(trip.getSupervisor());
    }

    public List<UserUsernameDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserUsernameDTO> participants) {
        this.participants = participants;
    }

    public UserUsernameDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UserUsernameDTO supervisor) {
        this.supervisor = supervisor;
    }
}
