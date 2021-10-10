package com.tasktracker.task;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Collection<Task> tasks() {
        return taskService.read();
    }

    @GetMapping("{taskId}")
    public Task getById(@PathVariable Long taskId) {
        return taskService.getById(taskId);
    }

    @PostMapping()
    public Task create(@Valid @RequestBody Task task) {
        return taskService.create(task);
    }

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long taskId, @RequestBody Task task) {
        return taskService.update(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

}
