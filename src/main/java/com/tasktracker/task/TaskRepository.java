package com.tasktracker.task;

import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TaskRepository implements TaskDao {
    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public Task update(Long id, Task task) {
        return null;
    }

    @Override
    public Task getById(Long id) {
        return null;
    }

    @Override
    public Collection<Task> getAll() {
        return null;
    }
}
