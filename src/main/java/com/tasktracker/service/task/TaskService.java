package com.tasktracker.service.task;

import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.service.CRUDService;

public interface TaskService extends CRUDService<TaskTO> {
    TaskTO getById(Long id);
}
