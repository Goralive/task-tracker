package com.tasktracker.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository implements UserDao {
    private final AtomicLong aLong;
    private final Map<Long, User> users;

    public UserRepository(AtomicLong aLong, Map<Long, User> users) {
        this.aLong = aLong;
        this.users = users;
    }

    @Override
    public User create(User user) {
        Long id = aLong.getAndIncrement();
        user.setId(id);
        users.put(id, user);
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
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
