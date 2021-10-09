package com.tasktracker.user;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserValidation {
    private final CommonDAO<User> userDao;

    public UserValidation(CommonDAO<User> userDao) {
        this.userDao = userDao;
    }

    public User isPresent(Long id) {
        return userDao.getAll()
                .stream()
                .filter(u -> u.getId().equals(id) && !u.isDeleted())
                .findAny().orElseThrow(() -> new UserNotFoundException("No user found"));
    }
}
