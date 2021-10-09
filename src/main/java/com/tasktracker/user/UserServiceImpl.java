package com.tasktracker.user;

import com.tasktracker.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao repository;
    private final EmailValidation emailValidation;
    private final UserValidation userValidation;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
        this.userValidation = new UserValidation(this.repository);
        this.emailValidation = new EmailValidation(this.repository);
    }

    @Override
    public User create(User user) {
        log.debug("Creating user {} with email {}", user.getName(), user.getEmail());
        String email = user.getEmail();
        emailValidation.isEmailValid(email)
                .isEmailRegistered(email);
        return repository.create(user);
    }

    @Override
    public User update(Long id, User user) {
        User foundUser = userValidation.isPresent(id);
        String email = user.getEmail();
        String name = user.getName();

        if (email == null) {
            user.setEmail(foundUser.getEmail());
        } else {
            emailValidation.isEmailValid(user.getEmail());
        }
        if (name == null) {
            user.setName(foundUser.getName());
        }
        log.debug("Update user {} with data {}", foundUser, user);
        return repository.update(id, user);
    }

    @Override
    public void delete(Long id) {
        User user = userValidation.isPresent(id);
        log.debug("Delete user {}", user);
        user.setDeleted(true);
    }

    @Override
    public UserTasks getUserById(Long id) {
        log.debug("Get user by id {}", id);
        User user = userValidation.isPresent(id);
        //TODO Temp data
        List<Task> tasks = new ArrayList<>();

        return new UserTasks(user, tasks);
    }

    @Override
    public List<User> read() {
        return repository.getAll().stream().filter(u -> !u.isDeleted())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
