package com.tasktracker.service.task;

import com.tasktracker.repository.IRepository;
import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskValidator {
    private final IRepository<UserEntity> users;

    public TaskValidator(IRepository<UserEntity> users) {
        this.users = users;
    }

    public void checkNew(TaskEntity task) {
        isReporterDeleted(task);
        isAssigneeDeleted(task);
    }

    private boolean isAssigneeDeleted(TaskEntity task) {
        UserEntity user = Optional.ofNullable(users.getById(task.getAssignee()))
                .orElseThrow(() -> new UserNotFoundException(task.getAssignee()));
        return user.isDeleted();
    }

    private boolean isReporterDeleted(TaskEntity task) {
        UserEntity user = Optional.ofNullable(users.getById(task.getReporter()))
                .orElseThrow(() -> new UserNotFoundException(task.getReporter()));
        return user.isDeleted();
    }
}
