package com.tasktracker.service.task;

import com.tasktracker.repository.IRepository;
import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.service.exception.TaskUpdateFieldValidationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final IRepository<TaskEntity> tasks;
    private final TaskValidator validation;

    public TaskServiceImpl(TaskRepository tasks, TaskValidator validation) {
        this.tasks = tasks;
        this.validation = validation;
    }

    @Override
    public TaskEntity create(TaskEntity task) {
        validation.checkNew(task);
        return tasks.create(task);
    }

    @Override
    public Collection<TaskEntity> fetchAll() {
        return tasks.getAll();
    }

    @Override
    public TaskEntity getById(Long id) {
        return Optional
                .ofNullable(tasks.getById(id))
                .orElseThrow(() -> new TaskException(id));
    }

    @Override
    public TaskEntity update(Long id, TaskEntity task) {
        TaskEntity createdTask = this.getById(id);
        String requestTitle = task.getTitle();
        String requestDescription = task.getDescription();

        if (requestDescription != null || requestTitle != null) {
            if (requestTitle != null) {
                createdTask.setTitle(requestTitle);
            }

            if (requestDescription != null) {
                createdTask.setDescription(requestDescription);
            }
        } else {
            throw new TaskUpdateFieldValidationException(id);
        }
        return tasks.update(id, createdTask);
    }

    @Override
    public void delete(Long id) {
        throw new TaskException(id);
    }
}
