package com.tasktracker.service.task;

import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.inmem.TaskRepository;
import com.tasktracker.service.exception.BaseApplicationException;
import com.tasktracker.service.exception.TaskException;
import com.tasktracker.service.exception.TaskUpdateFieldValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskValidator validation;

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);


    public TaskServiceImpl(TaskRepository taskRepository, TaskValidator validation) {
        this.taskRepository = taskRepository;
        this.validation = validation;
    }

    @Override
    public TaskTO create(TaskTO task) {
        validation.checkNew(task);

        TaskEntity toSave = new TaskEntity(task.id, task.title,
                task.description, task.reporter);
        if (task.assignee != null) {
            toSave.setAssignee(task.assignee);
        }

        TaskEntity saved = taskRepository.create(toSave);
        return TaskTO.fromEntity(saved);
    }

    @Override
    public Collection<TaskTO> fetchAll() {
        return taskRepository.getAll().stream()
                .map(TaskTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TaskTO getById(Long id) {
        return Optional
                .ofNullable(taskRepository.getById(id))
                .map(TaskTO::fromEntity)
                .orElseThrow(() -> new TaskException(id));
    }

    @Override
    public TaskTO update(Long id, TaskTO task) {
        TaskEntity toSave = Optional
                .ofNullable(taskRepository.getById(id))
                .orElseThrow(() -> new TaskException(id));

        String requestTitle = task.title;
        String requestDescription = task.description;

        if (requestDescription != null || requestTitle != null) {
            if (requestTitle != null) {
                toSave.setTitle(requestTitle);
            }

            if (requestDescription != null) {
                toSave.setDescription(requestDescription);
            }
        } else {
            throw new TaskUpdateFieldValidationException(id);
        }
        TaskEntity saved = taskRepository.update(id, toSave);
        return TaskTO.fromEntity(saved);
    }

    @Override
    public void delete(Long id) {
        throw new BaseApplicationException("Tasks can't be deleted");
    }
}
