package com.esgi.tp_spring.repositories;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.username LIKE ?1 OR u.firstName LIKE ?1 OR u.lastName LIKE ?1")
    Page<User> simpleSearch(String filter, Pageable pageable);
}