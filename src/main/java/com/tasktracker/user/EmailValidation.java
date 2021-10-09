package com.tasktracker.user;

import com.tasktracker.exception.EmailException;

import java.util.regex.Pattern;

public class EmailValidation {
    private final Pattern emailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final UserDao userDao;

    public EmailValidation(UserDao userDao) {
        this.userDao = userDao;
    }

    public EmailValidation isEmailRegistered(String email) {
        isEmailValid(email);
        userDao.getAll()
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email) && !user.isDeleted())
                .findAny()
                .ifPresent(e -> {
                    throw new EmailException("Email " + email + " not unique");
                });
        return this;
    }

    public EmailValidation isEmailValid(String email) {
        if (!emailRegex.matcher(email).find()) {
            throw new EmailException("Invalid email " + email);
        }
        return this;
    }
}
