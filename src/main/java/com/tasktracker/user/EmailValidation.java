package com.tasktracker.user;

import com.tasktracker.exception.EmailException;

import java.util.List;
import java.util.regex.Pattern;

public class EmailValidation {
    private final Pattern emailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void isEmailRegistered(String email, List<User> users) {
        users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findAny()
                .ifPresent(e -> {
                    throw new EmailException("Email " + email + " not unique");
                });
    }

    public void isEmailValid(String email) {
        if (!emailRegex.matcher(email).find()) {
            throw new EmailException("Invalid email " + email);
        }
    }
}
