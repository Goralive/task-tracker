package com.tasktracker.service.exception;

public class TaskException extends BaseApplicationException {
    public TaskException(Long taskId) {
        super(String.format("No task found for id %d", taskId));
    }
}
