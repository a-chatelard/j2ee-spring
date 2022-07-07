package com.esgi.tp_spring.dto.requests;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.TripComplexity;
import com.esgi.tp_spring.entities.User;

import javax.validation.constraints.*;
import java.util.Date;

public class TripRequestDTO implements IRequestDTO<Trip>{
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Future
    private Date date;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer duration;

    private TripComplexity complexity;

    private String location;

    @NotNull
    private String supervisorUsername;

    @Override
    public Trip ToEntity() {
        var trip = new Trip();

        if (this.id != null) {
            trip.setId(this.id);
        }

        trip.setName(this.name);
        trip.setDescription(this.description);
        trip.setDate(this.date);
        trip.setDuration(this.duration);
        trip.setComplexity(this.complexity);
        trip.setLocation(this.location);

        var supervisor = new User();
        supervisor.setUsername(this.supervisorUsername);

        trip.setSupervisor(supervisor);

        return trip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public TripComplexity getComplexity() {
        return complexity;
    }

    public void setComplexity(TripComplexity complexity) {
        this.complexity = complexity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupervisorUsername() {
        return supervisorUsername;
    }

    public void setSupervisorUsername(String supervisorUsername) {
        this.supervisorUsername = supervisorUsername;
    }
}
