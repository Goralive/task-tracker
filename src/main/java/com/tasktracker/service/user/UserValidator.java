package com.tasktracker.service.user;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.repository.inmem.UserRepository;
import com.tasktracker.service.exception.EmailException;
import com.tasktracker.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidator {
    private final Pattern emailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateNew(UserTO user) {
        isEmailValid(user.email);
        isEmailRegistered(user.email);
    }


    public void checkUpdate(Long requestedUserId, UserTO dataForUpdate) {
        findById(requestedUserId);
        String email = dataForUpdate.email;
        if (email != null) {
            isEmailValid(email);
            isEmailRegistered(email);
        }
    }

    public void findById(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        if (userEntity == null || userEntity.isDeleted()) {
            throw new UserNotFoundException(id);
        }
    }

    private void isEmailRegistered(String email) {
        if (userRepository.getByEmail(email) != null && !userRepository.getByEmail(email).isDeleted()) {
            throw new EmailException(email);
        }
    }

    public void isEmailValid(String email) {
        if (!emailRegex.matcher(email).find()) {
            throw new EmailException(email);
        }
    }
}
