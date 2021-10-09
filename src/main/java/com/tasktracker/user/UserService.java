package com.tasktracker.user;

import com.tasktracker.common.CRUDService;

public interface UserService extends CRUDService<User> {
    UserTasks getUserById(Long id);

}
