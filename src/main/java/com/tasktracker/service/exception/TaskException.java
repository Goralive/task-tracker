package com.tasktracker.service.exception;

public class TaskException extends BaseApplicationException {
    public TaskException(Long taskId) {
        super(String.format("No task found for id %d", taskId));
    }

    public TaskException(String field) {
        super(String.format("%s is mandatory", field));
    }
}
