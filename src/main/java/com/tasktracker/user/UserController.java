package com.tasktracker.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }
}
