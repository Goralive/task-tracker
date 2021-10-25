package com.tasktracker.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.service.task.TaskTO;

import java.util.List;

public class UserTaskTO {

    public final UserTO user;
    public final List<TaskTO> tasks;

    @JsonCreator
    public UserTaskTO(UserTO user, List<TaskTO> tasks) {
        this.user = user;
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserTaskTO{");
        sb.append("user=").append(user);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
    }
}
