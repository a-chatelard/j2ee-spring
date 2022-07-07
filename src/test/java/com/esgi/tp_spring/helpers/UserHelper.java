package com.esgi.tp_spring.helpers;

import com.esgi.tp_spring.entities.User;
import com.esgi.tp_spring.entities.UserRole;
import com.esgi.tp_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHelper {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String username) {
        var user = new User();

        user.setUsername(username);
        user.setLastName("test");
        user.setFirstName("user");
        user.setUserRole(UserRole.USER);
        userRepository.save(user);

        return user;
    }

    public void removeUser(String username) {
        userRepository.deleteById(username);
    }
}

