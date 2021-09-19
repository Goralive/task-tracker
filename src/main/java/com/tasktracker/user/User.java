package com.tasktracker.user;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    public void setId(Long id) {
        this.id = id;
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
