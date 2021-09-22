package com.tasktracker.user;

import com.tasktracker.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao repository;
    private final EmailValidation emailValidation;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository repository, EmailValidation emailValidation) {
        this.repository = repository;
        this.emailValidation = emailValidation;
    }

    @Override
    public User create(User user) {
        log.debug("Creating user {} with email {}", user.getName(), user.getEmail());
        String email = user.getEmail();
        emailValidation.isEmailRegistered(email, getAllUsers());
        return repository.create(user);
    }

    @Override
    public User update(Long id, User user) {
        log.debug("Update user {} with data {}", id, user);

        return null;
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
