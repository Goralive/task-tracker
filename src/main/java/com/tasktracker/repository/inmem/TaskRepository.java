package com.tasktracker.repository.inmem;

import com.tasktracker.configuration.Resettable;
import com.tasktracker.repository.IRepository;
import com.tasktracker.repository.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TaskRepository implements IRepository<TaskEntity>, Resettable {
    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, TaskEntity> tasksById = new HashMap<>();
    private final Map<Long, TaskEntity> assignedTasks = new HashMap<>();

    @Override
    public TaskEntity create(TaskEntity task) {
        if (task.getId() == null) {
            Long id = counter.incrementAndGet();
            task.setId(id);
            if (task.getAssignee() != null) {
                assignedTasks.put(task.getId(), task);
            }
        }
        tasksById.put(task.getId(), task);
        log.info("Create task {}", task);
        return task;
    }

    @Override
    public TaskEntity update(Long id, TaskEntity task) {
        return tasksById.put(id, task);
    }

    @Override
    public TaskEntity getById(Long taskId) {
        return tasksById.get(taskId);
    }

    @Override
    public Collection<TaskEntity> getAll() {
        log.info("Getting all tasks. Size is {}", tasksById.size());
        return new ArrayList<>(tasksById.values());
    }

    public Collection<TaskEntity> getTasksByAssigneeId(Long id) {
        log.info("Get tasks for assignee {}", id);
        return tasksById.values()
                .stream()
                .filter(t -> t.getAssignee() != null && t.getAssignee().equals(id))
                .collect(Collectors.toList());
    }

    public TaskEntity assignTask(Long taskId, TaskEntity task) {
        return assignedTasks.put(taskId, task);
    }

    @Override
    public void reset() {
        counter.set(0);
        tasksById.clear();
        assignedTasks.clear();
    }
}
