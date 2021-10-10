package com.tasktracker.task;

import com.tasktracker.common.CRUDService;

public interface TaskService extends CRUDService<Task> {
    Task getById(Long id);
}
