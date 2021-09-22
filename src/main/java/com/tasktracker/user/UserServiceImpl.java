package com.tasktracker.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao repository;
    private final EmailValidation emailValidation;
    private final UserValidation userValidation;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository repository, EmailValidation emailValidation, UserValidation user) {
        this.repository = repository;
        this.emailValidation = emailValidation;
        this.userValidation = user;
    }

    @Override
    public User create(User user) {
        log.debug("Creating user {} with email {}", user.getName(), user.getEmail());
        String email = user.getEmail();
        emailValidation.isEmailValid(email);
        emailValidation.isEmailRegistered(email, getAllUsers());
        return repository.create(user);
}

    @Override
    public User update(Long id, User user) {
        log.debug("Update user {} with data {}", id, user);
        userValidation.isPresent(id, getAllUsers());
        emailValidation.isEmailValid(user.getEmail());
        return repository.update(id, user);
    }

    @Override
    public boolean deleteUserById(Long id) {
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }
}
