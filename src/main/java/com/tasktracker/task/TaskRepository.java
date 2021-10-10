package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository implements CommonDAO<Task> {
    private final AtomicLong taskId;
    private final Map<Long, Task> tasks;
    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);


    public TaskRepository(AtomicLong aLong, Map<Long, Task> tasks) {
        this.taskId = aLong;
        this.tasks = tasks;
    }

    @Override
    public Task create(Task task) {
        Long id = taskId.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        log.debug("Create task {}", task);
        return task;
    }

    @Override
    public Task update(Long id, Task task) {
        return tasks.put(id,task);
    }

    @Override
    public Task getById(Long id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> getAll() {
        log.info("Getting all tasks. Size is {}", tasks.size());
        return new ArrayList<>(tasks.values());
    }
}
