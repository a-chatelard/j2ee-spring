package com.esgi.tp_spring.dto.results;

import com.esgi.tp_spring.entities.User;

public class UserUsernameDTO {
    private String username;

    public UserUsernameDTO() {}

    public UserUsernameDTO(User user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
