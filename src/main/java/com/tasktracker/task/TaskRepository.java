package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.user.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository implements CommonDAO<Task> {
    private final AtomicLong aLong;
    private final Map<Long, Task> tasks;

    public TaskRepository(AtomicLong aLong, Map<Long, Task> tasks) {
        this.aLong = aLong;
        this.tasks = tasks;
    }

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
