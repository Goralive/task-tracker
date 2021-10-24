package com.tasktracker.service.user;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.EmailException;
import com.tasktracker.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateNew(UserEntity user) {
        isEmailRegistered(user.getEmail());
    }

    private void isEmailRegistered(String email) {
        UserEntity byEmail = userRepository.getByEmail(email);
        if (byEmail != null) {
            throw new EmailException(email);
        }
    }

    public void checkUpdate(Long requestedUserId, UserTO dataForUpdate) {
        findById(requestedUserId);
        isEmailRegistered(dataForUpdate.email);
    }

    public void findById(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        if (userEntity == null || userEntity.isDeleted()) {
            throw new UserNotFoundException(id);
        }
    }
}
