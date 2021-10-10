package com.tasktracker.user;

import com.tasktracker.common.CRUDService;
import com.tasktracker.task.Task;

public interface UserService extends CRUDService<User> {
    UserTasks getUserById(Long id);

    Task assignTask(Long userId, Long taskId);
}
