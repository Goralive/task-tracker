package com.tasktracker.user;

import com.tasktracker.task.Task;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository implements UserDao {
    private final AtomicLong id = new AtomicLong();
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        users.put(id.getAndIncrement(), user);
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Task getUser(Long id) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }
}
