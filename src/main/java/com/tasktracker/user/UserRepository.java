package com.tasktracker.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository implements UserDao {
    private final AtomicLong aLong;
    private final Map<Long, User> users;
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(AtomicLong aLong, Map<Long, User> users) {
        this.aLong = aLong;
        this.users = users;
    }

    @Override
    public User create(User user) {
        Long id = aLong.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        log.debug("Create user {}", user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        log.debug("Update user {}", user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        log.info("Get user by id {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users. Size is {}", users.size());
        return new ArrayList<>(users.values());
    }
}
