package com.tasktracker.user;

import java.util.Collection;

public interface UserDao {

    User create(final User user);

    User update(final User user);

    User getUserById(final Long id);

    Collection<User> getAllUsers();
}
