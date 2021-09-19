package com.tasktracker.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.task.Task;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private Long id;
    private String name;
    private String email;
    private boolean deleted;

    @JsonCreator
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
