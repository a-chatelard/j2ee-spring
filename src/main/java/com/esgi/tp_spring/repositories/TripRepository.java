package com.esgi.tp_spring.repositories;


import com.esgi.tp_spring.entities.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    Page<Trip> findByParticipants_Username(String username, Pageable pageable);

    @Query("SELECT t FROM Trip t WHERE t.name LIKE ?1 OR t.description LIKE ?1 OR t.location LIKE ?1")
    Page<Trip> simpleSearch(String filter, Pageable pageable);

    @Query("SELECT t FROM Trip t WHERE FUNCTION('MONTH', t.date) = ?1 AND FUNCTION('YEAR', t.date) = ?2")
    Page<Trip> findByMonthAndYear(Integer month, Integer year, Pageable pageable);

    Page<Trip> findByParticipantsEmpty(Pageable pageable);
}