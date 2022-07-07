package com.esgi.tp_spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "firstname")
    @NotBlank
    private String firstName;

    @Column(name = "lastname")
    @NotBlank
    private String lastName;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Address> addresses = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    private Set<Trip> tripsParticipations = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "supervisor")
    private Set<Trip> tripsSupervisions = new HashSet<>();

    @Column(name = "role")
    @Enumerated
    private UserRole userRole;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Trip> getTripsParticipations() {
        return tripsParticipations;
    }

    public void setTripsParticipations(Set<Trip> tripsParticipations) {
        this.tripsParticipations = tripsParticipations;
    }

    public Set<Trip> getTripsSupervisions() {
        return tripsSupervisions;
    }

    public void setTripsSupervisions(Set<Trip> tripsSupervisions) {
        this.tripsSupervisions = tripsSupervisions;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
