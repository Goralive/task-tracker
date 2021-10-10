package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.exception.TaskException;
import com.tasktracker.user.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {
    private final CommonDAO<User> users;
    private final CommonDAO<Task> tasks;
    private final TaskValidation validation;

    public TaskServiceImpl(CommonDAO<User> users, CommonDAO<Task> tasks, TaskValidation validation) {
        this.users = users;
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
    public Task update(Long id, Task entity) {
        return null;
    }

    @Override
    public void delete(Long id) {
        throw new TaskException("Tasks can't be deleted");
    }

    @Override
    public void assignTask(Task task) {
        task.setAssignee(task.getAssignee());
    }
}
