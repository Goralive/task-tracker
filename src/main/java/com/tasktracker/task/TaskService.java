package com.tasktracker.task;

import java.util.Collection;

public interface TaskService {

    Task create(Task task);

    boolean assignTask(Long userId, Long taskId);

    Task update(Task task);

    Task getTaskById();

    Collection<Task> getAllTasks();
}
