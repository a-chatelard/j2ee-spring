package com.esgi.tp_spring.dto.results;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.TripComplexity;

import java.util.Date;

public class TripDTO {
    protected Long id;

    protected String name;

    protected String description;

    protected Date date;

    protected Integer duration;

    protected TripComplexity complexity;

    protected String location;

    public TripDTO() {}

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.description = trip.getDescription();
        this.date = trip.getDate();
        this.duration = trip.getDuration();
        this.complexity = trip.getComplexity();
        this.location = trip.getLocation();
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
}
