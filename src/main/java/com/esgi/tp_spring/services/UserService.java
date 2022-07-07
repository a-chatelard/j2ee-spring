package com.esgi.tp_spring.services;

import com.esgi.tp_spring.dto.requests.UserRequestDTO;
import com.esgi.tp_spring.dto.results.UserDetailsDTO;
import com.esgi.tp_spring.dto.results.TripDTO;
import com.esgi.tp_spring.dto.results.UserDTO;
import com.esgi.tp_spring.entities.User;
import com.esgi.tp_spring.repositories.TripRepository;
import com.esgi.tp_spring.repositories.UserRepository;
import com.esgi.tp_spring.services.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsDTO createOrUpdate(UserRequestDTO userDTO) {
        var user = userDTO.ToEntity();

        logger.info("Saving user of username " + user.getUsername());

        if (StringUtils.isNotEmpty(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        logger.info("User of username " + user.getUsername() + " saved.");
        userRepository.save(user);

        return new UserDetailsDTO(user);
    }

    public User getById(String username) throws ResourceNotFoundException {
        var user = userRepository.findById(username);
        if (user.isPresent()) {
            return user.get();
        }
        logger.warn("User of username " + username + " not found.");
        throw new ResourceNotFoundException(User.class, username);
    }

    public UserDetailsDTO getInfosById(String username) throws ResourceNotFoundException {
        var user = getById(username);

        return new UserDetailsDTO(user);
    }

    public Page<UserDTO> getAllPaged(Pageable pageable) {
        var result = userRepository.findAll(pageable);

        return result.map(UserDTO::new);
    }

    public Page<UserDTO> getAllFilteredAndPaged(String filter, Pageable pageable) {
        var result = userRepository.simpleSearch(filter, pageable);
        return result.map(UserDTO::new);
    }

    public Page<TripDTO> getTripsParticipationsPaged(String username, Pageable pageable) throws ResourceNotFoundException {
        if (!userRepository.existsById(username)) {
            throw new ResourceNotFoundException(User.class, username);
        }

        var result = tripRepository.findByParticipants_Username(username, pageable);

        return result.map(TripDTO::new);
    }

    public void deleteById(String username) throws ResourceNotFoundException {
        logger.info("Deleting user of username " + username);
        var user = userRepository.findById(username);
        if (user.isPresent()) {
            userRepository.deleteById(username);
            logger.info("User of username " + username + " deleted.");
        } else {
            logger.error("User of username " + username + " not found.");
            throw new ResourceNotFoundException(User.class, username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        try {
            user = getById(username);
        } catch (ResourceNotFoundException e) {
            logger.error("User of username " + username + " not found.");
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
        logger.info("Updating password of user " + username);
        User user;
        try {
            user = getById(username);
        } catch (ResourceNotFoundException e) {
            logger.error("User of username " + username + " not found.");
            throw new IllegalAccessException(e.getMessage());
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        logger.info("Password of user " + username + " updated.");
    }
}