package com.tasktracker.service.task;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskValidator {
    private final UserRepository users;

    public TaskValidator(UserRepository users) {
        this.users = users;
    }

    public void checkNew(TaskTO task) {
        if (isDeleted(task.reporter)) {
            throw new UserNotFoundException(task.reporter, "Reporter");
        } else if (task.assignee != null && isDeleted(task.assignee)) {
            throw new UserNotFoundException(task.assignee, "Assignee");
        }
    }

    private boolean isDeleted(Long id) {
        UserEntity user = Optional.ofNullable(users.getById(id))
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.isDeleted();
    }
}
