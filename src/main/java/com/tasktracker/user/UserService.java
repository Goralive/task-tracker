package com.tasktracker.user;

import java.util.Collection;

public interface UserService {
    User create(User user);

    User update(User user);

    boolean deleteUserById(Long id);

    User getUserById(Long id);

    Collection<User> getAllUsers();
}
