package com.tasktracker.user;

import com.tasktracker.exception.UserNotFoundException;

public class UserValidation {
    private final UserDao userDao;

    public UserValidation(UserDao userDao) {
        this.userDao = userDao;
    }

    public User isPresent(Long id) {
        return userDao.getAll()
                .stream()
                .filter(u -> u.getId().equals(id) && !u.isDeleted())
                .findAny().orElseThrow(() -> new UserNotFoundException("No user found"));
    }
}
