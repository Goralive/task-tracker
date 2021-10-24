package com.tasktracker.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.repository.entity.TaskEntity;
import com.tasktracker.repository.entity.UserEntity;

import java.util.List;

public class UserTaskTO {

    public final UserEntity user;
    public final List<TaskEntity> tasks;

    @JsonCreator
    public UserTaskTO(UserEntity user, List<TaskEntity> tasks) {
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
