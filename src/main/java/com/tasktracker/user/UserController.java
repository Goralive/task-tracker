package com.tasktracker.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    public User create(@RequestBody User user) {
        return service.create(user);
    }
}
