package com.tasktracker.service.user;

import com.tasktracker.service.CRUDService;
import com.tasktracker.service.task.TaskTO;

public interface UserService extends CRUDService<UserTO> {
    UserTaskTO getUserById(Long id);

    TaskTO assignTask(Long userId, Long taskId);

}
