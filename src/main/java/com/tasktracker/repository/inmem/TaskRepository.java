package com.tasktracker.repository.inmem;

import com.tasktracker.repository.IRepository;
import com.tasktracker.repository.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository implements IRepository<TaskEntity> {
    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, TaskEntity> tasks = new HashMap<>();

    @Override
    public TaskEntity create(TaskEntity task) {
        if (task.getId() == null) {
            Long id = counter.incrementAndGet();
            task.setId(id);
            if (task.getAssignee() != null) {
//                assignedTasks.put(task.getId(), tasks);
            }
        }
        tasks.put(task.getId(), task);
        log.info("Create task {}", task);
        return task;
    }

    @Override
    public TaskEntity update(Long id, TaskEntity task) {
        return tasks.put(id, task);
    }

    @Override
    public TaskEntity getById(Long taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Collection<TaskEntity> getAll() {
        log.info("Getting all tasks. Size is {}", tasks.size());
        return new ArrayList<>(tasks.values());
    }

    public List<TaskEntity> getByAssigneeId(Long id) {
        return  null;
    }
}
