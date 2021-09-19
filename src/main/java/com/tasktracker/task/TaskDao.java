package com.tasktracker.task;

import java.util.Collection;

public interface TaskDao {
    Task create(final Task task);

    Task update(final Task task);

    Task getTask(final Long id);

    Collection<Task> getAllTasks();
}
