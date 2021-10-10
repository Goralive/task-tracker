package com.tasktracker.user;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.exception.TaskException;
import com.tasktracker.task.Task;
import com.tasktracker.task.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final CommonDAO<User> users;
    private final CommonDAO<Task> tasks;

    private final UserValidation userValidation;
    private final EmailValidation emailValidation;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository users, TaskRepository tasks,
                           UserValidation userValidation, EmailValidation emailValidation) {
        this.users = users;
        this.tasks = tasks;

        this.userValidation = userValidation;
        this.emailValidation = emailValidation;
    }

    @Override
    public User create(User user) {
        log.debug("Creating user {} with email {}", user.getName(), user.getEmail());
        String email = user.getEmail();
        emailValidation.isEmailValid(email)
                .isEmailRegistered(email);
        return users.create(user);
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
        return users.update(id, user);
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
        List<Task> taskList = tasks.getAll()
                .stream()
                .filter(t -> t.getAssignee().equals(user.getId()))
                .collect(Collectors.toList());

        return new UserTasks(user, taskList);
    }

    @Override
    public Task assignTask(Long userId, Long taskId) {
        userValidation.isPresent(userId);
        Task task = Optional.ofNullable(tasks.getById(taskId))
                .map(task1 -> {
                    task1.setAssignee(userId);
                    return task1;
                })
                .orElseThrow(() -> new TaskException("No task was found. Task can't be assigned to user"));
        return task;
    }

    @Override
    public List<User> read() {
        return users.getAll().stream().filter(u -> !u.isDeleted())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
