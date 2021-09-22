package com.tasktracker.user;

import java.util.List;

public interface UserDao {

    User create(final User user);

    User update(final Long id, final User user);

    User getUserById(final Long id);

    List<User> getAllUsers();
}
