package com.tasktracker.service.exception;

public class EmailException extends BaseApplicationException {
    public EmailException(String email) {
        super(String.format("Email '%s' not unique or invalid. Please try again", email));
    }
}
