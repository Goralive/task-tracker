package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.exception.TaskException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final CommonDAO<Task> tasks;
    private final TaskValidation validation;

    public TaskServiceImpl(TaskRepository tasks, TaskValidation validation) {
        this.tasks = tasks;
        this.validation = validation;
    }

    @Override
    public Task create(Task task) {
        validation.shouldCreateTask(task);
        return tasks.create(task);
    }

    @Override
    public Collection<Task> read() {
        return tasks.getAll();
    }

    @Override
    public Task getById(Long id) {
        return Optional
                .ofNullable(tasks.getById(id))
                .orElseThrow(() -> new TaskException("No task was found"));

    }

    @Override
    public Task update(Long id, Task task) {
        Task createdTask = this.getById(id);
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
            throw new TaskException("Can be updated only description or title");
        }
        return tasks.update(id, createdTask);
    }

    @Override
    public void delete(Long id) {
        throw new TaskException("Tasks can't be deleted");
    }
}
