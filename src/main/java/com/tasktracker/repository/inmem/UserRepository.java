package com.tasktracker.repository.inmem;

import com.tasktracker.configuration.ResetTable;
import com.tasktracker.repository.IRepository;
import com.tasktracker.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class UserRepository implements IRepository<UserEntity>, ResetTable {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, UserEntity> usersById = new HashMap<>();
    private final Map<String, UserEntity> usersByEmail = new HashMap<>();

    @Override
    public UserEntity create(UserEntity user) {
        if (user.getId() == null) {
            Long id = counter.incrementAndGet();
            user.setId(id);
        }
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
        log.info("Create user {}", user);
        return user;
    }

    @Override
    public UserEntity update(Long id, UserEntity user) {
        usersById.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
        log.info("Updated user {} with data {}", id, user);
        return user;
    }

    @Override
    public UserEntity getById(Long id) {
        log.info("Get user by id {}", id);
        return usersById.get(id);
    }

    public UserEntity getByEmail(String email) {
        log.info("Get user by email {}", email);
        return usersByEmail.get(email);
    }

    @Override
    public Collection<UserEntity> getAll() {
        log.info("Getting all users. Size is {}", usersById.size());
        return new ArrayList<>(usersById.values());
    }

    @Override
    public void reset() {
        counter.set(0);
        usersById.clear();
        usersByEmail.clear();
    }
}
