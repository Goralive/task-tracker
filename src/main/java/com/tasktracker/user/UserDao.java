package com.tasktracker.user;

import com.tasktracker.task.Task;

import java.util.Collection;

public interface UserDao {

    User create(final User user);

    User update(final User user);

    Task getUser(final Long id);

    Collection<User> getAllUsers();
}
