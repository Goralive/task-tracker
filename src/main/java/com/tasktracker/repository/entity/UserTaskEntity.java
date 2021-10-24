package com.tasktracker.repository.entity;

import java.util.List;

public class UserTaskEntity {
    private UserEntity user;
    private List<TaskEntity> tasks;

    public UserTaskEntity(UserEntity user, List<TaskEntity> tasks) {
        this.user = user;
        this.tasks = tasks;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserTaskEntity{");
        sb.append("user=").append(user);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
    }
}
