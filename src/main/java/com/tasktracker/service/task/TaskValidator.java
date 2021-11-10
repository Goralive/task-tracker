package com.tasktracker.service.task;

import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TaskValidator {
    private final UserRepository users;
    private final TaskRepository taskRepository;

    public TaskValidator(UserRepository users, TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.users = users;
    }

    public void checkNew(TaskTO task) {
        if (isDeleted(task.reporter)) {
            throw new UserNotFoundException(task.reporter, "Reporter");
        } else if (task.assignee != null && isDeleted(task.assignee)) {
            throw new UserNotFoundException(task.assignee, "Assignee");
        }
        if (task.title == null || task.title.isEmpty()) {
            throw new TaskException("title");
        }
        if (task.description == null || task.description.isEmpty()) {
            throw new TaskException("description");
        }
    }

    private boolean isDeleted(Long id) {
        UserEntity user = Optional.ofNullable(users.getById(id))
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.isDeleted();
    }

    public TaskEntity checkUpdate(Long id) {
        return Optional
                .ofNullable(taskRepository.getById(id))
                .orElseThrow(() -> new TaskException(id));

    }
}
