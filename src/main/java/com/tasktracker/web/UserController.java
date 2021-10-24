package com.tasktracker.web;

import com.tasktracker.service.task.TaskTO;
import com.tasktracker.service.user.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public Collection<UserTO> users() {
        return service.fetchAll();
    }

    @GetMapping("/{userId}")
    public UserTaskTO getUserWithTasksById(@PathVariable Long userId) {
        return service.getUserByIdWithTasks(userId);
    }

    @PostMapping
    public UserTO create(@Valid @RequestBody UserTO user) {
        return service.create(user);
    }

    @PutMapping("/{userId}")
    public UserTO update(@PathVariable Long userId, @RequestBody UserTO user) {
        return service.update(userId, user);
    }

    @PostMapping("/{userId}/tasks/{taskId}")
    public TaskTO assignTask(@PathVariable Long userId, @PathVariable Long taskId) {
        return service.assignTask(userId, taskId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        service.delete(userId);
    }
}
