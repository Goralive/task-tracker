package com.tasktracker.user;

import java.util.Collection;

public interface UserService {
    User create(User user);

    User update(Long id,User user);

    void deleteUserById(Long id);

    UserTasks getUserById(Long id);

    Collection<User> getAllUsers();
}
