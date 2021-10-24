package com.tasktracker.service.exception;

public class EmailException extends BaseApplicationException {
    public EmailException(String email) {
        super(String.format("Email '%s' not unique. Please try again", email));
    }
}
