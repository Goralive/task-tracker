package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import com.tasktracker.exception.TaskException;
import com.tasktracker.exception.UserNotFoundException;
import com.tasktracker.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskValidation {
    private final CommonDAO<User> users;

    public TaskValidation(CommonDAO<User> users) {
        this.users = users;
    }

    public void shouldCreateTask(Task task) {
        if (isReporterDeleted(task)) {
            throw new TaskException("Reporter can't be set to the task. The user was deleted");
        }

        if (task.getAssignee() != null && isAssigneeDeleted(task)) {
            throw new TaskException("Assignee can't be set to the task. The user was deleted");
        }
    }

    private boolean isAssigneeDeleted(Task task) {
        User user = Optional.ofNullable(users.getById(task.getAssignee()))
                .orElseThrow(() -> new UserNotFoundException("No assignee was found during task assignment"));
        return user.isDeleted();
    }

    private boolean isReporterDeleted(Task task) {
        User user = Optional.ofNullable(users.getById(task.getReporter()))
                .orElseThrow(() -> new UserNotFoundException("No user was found during task creation"));
        return user.isDeleted();
    }
}
