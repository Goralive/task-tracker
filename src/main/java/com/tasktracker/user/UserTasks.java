package com.tasktracker.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.task.Task;

import java.util.List;

public class UserTasks {
    private User user;
    private List<Task> tasks;

    @JsonCreator
    public UserTasks(User user, List<Task> tasks) {
        this.user = user;
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserTasks{");
        sb.append("user=").append(user);
        sb.append(", tasks=").append(tasks);
        sb.append('}');
        return sb.toString();
    }
}
