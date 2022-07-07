package com.esgi.tp_spring.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="date")
    @NotNull
    @Future
    private Date date;

    @Column(name="duration")
    @NotNull
    @Min(1)
    @Max(12)
    private Integer duration;

    @Column(name="complexity")
    @Enumerated
    private TripComplexity complexity;

    @Column(name="location")
    private String location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supervisor")
    @NotNull
    private User supervisor;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants;

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

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }
}
