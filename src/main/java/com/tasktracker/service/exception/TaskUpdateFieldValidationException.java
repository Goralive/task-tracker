package com.tasktracker.service.exception;

public class TaskUpdateFieldValidationException extends BaseApplicationException {
    public TaskUpdateFieldValidationException(Long taskId) {
        super(String.format("Can't be updated only description or title for task %d", taskId));
    }
}
