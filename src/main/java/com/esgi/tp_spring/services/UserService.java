package com.esgi.tp_spring.services;

import com.esgi.tp_spring.entities.Trip;
import com.esgi.tp_spring.entities.User;
import com.esgi.tp_spring.repositories.TripRepository;
import com.esgi.tp_spring.repositories.UserRepository;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createOrUpdate(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User getById(String username) throws ResourceNotFoundException {
        var user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResourceNotFoundException(User.class, username);
    }

    public Page<User> getAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getAllFilteredAndPaged(String filter, Pageable pageable) {
        return userRepository.simpleSearch(filter, pageable);
    }

    public Page<Trip> getTripsParticipationsPaged(String username, Pageable pageable) throws ResourceNotFoundException {
        if (!userRepository.existsById(username)) {
            throw new ResourceNotFoundException(User.class, username);
        }

        return tripRepository.findByParticipants_Username(username, pageable);
    }

    public void deleteById(String username) throws ResourceNotFoundException {
        var user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new ResourceNotFoundException(User.class, username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        try {
            user = getById(username);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

    public void setPassword(String username, String newPassword) throws IllegalAccessException {
        User user;
        try {
            user = getById(username);
        } catch (ResourceNotFoundException e) {
            throw new IllegalAccessException(e.getMessage());
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }
}