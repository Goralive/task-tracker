package com.tasktracker.task;

import com.tasktracker.common.CommonDAO;
import org.springframework.stereotype.Service;

@Service
public class TaskValidation {
    private final CommonDAO<Task> tasks;

    public TaskValidation(CommonDAO<Task> tasks) {
        this.tasks = tasks;
    }

}
