package com.tasktracker.user;

import com.tasktracker.exception.EmailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        String email = user.getEmail();
        if (!isEmailRegistered(email)) {
            return repository.create(user);
        } else {
            throw new EmailException("Email not unique");
        }
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
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    private boolean isEmailRegistered(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (matcher.find()) {
            return getAllUsers().stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
        }
        throw new EmailException("Invalid email");
    }
}
