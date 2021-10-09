package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;

import java.util.Collection;

public interface TaskDao extends CommonDAO<Task> {
    Task create(final Task task);

    Task update(final Long id, final Task task);

    Task getById(final Long id);

    Collection<Task> getAll();
}
