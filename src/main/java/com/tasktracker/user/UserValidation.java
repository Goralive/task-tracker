package com.tasktracker.user;

import com.tasktracker.exception.UserNotFoundException;

import java.util.List;

public class UserValidation {
    public void isPresent(Long id, List<User> users) {
        users
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElseThrow(() -> new UserNotFoundException("No user found"));
    }
}
