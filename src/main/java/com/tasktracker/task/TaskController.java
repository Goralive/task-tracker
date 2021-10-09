package com.tasktracker.task;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }
}
