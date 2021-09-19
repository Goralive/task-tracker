package com.tasktracker.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository implements UserDao {
    private final AtomicLong id;
    private final Map<Long, User> users;

    public UserRepository(AtomicLong id, Map<Long, User> users) {
        this.id = id;
        this.users = users;
    }

    @Override
    public User create(User user) {
        users.put(id.getAndIncrement(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return users.get(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
