package com.tasktracker.web;

import com.tasktracker.service.task.TaskService;
import com.tasktracker.service.task.TaskTO;
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
    public Collection<TaskTO> tasks() {
        return taskService.fetchAll();
    }

    @GetMapping("{taskId}")
    public TaskTO getById(@PathVariable Long taskId) {
        return taskService.getById(taskId);
    }

    @PostMapping()
    public TaskTO create(@Valid @RequestBody TaskTO task) {
        return taskService.create(task);
    }

    @PutMapping("/{taskId}")
    public TaskTO update(@PathVariable Long taskId, @RequestBody TaskTO task) {
        return taskService.update(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

}
