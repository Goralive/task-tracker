package com.tasktracker.service.exception;

public class UserNotFoundException extends BaseApplicationException {
    public UserNotFoundException(Long userId) {
        super(String.format("User not found. User id '%d'", userId));
    }
}