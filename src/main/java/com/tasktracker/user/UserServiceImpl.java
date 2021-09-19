package com.tasktracker.user;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        return repository.create(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }
}
