package com.tasktracker.service.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.repository.entity.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public class UserTO {
    public final Long id;
    @NotBlank
    public final String name;
    @NotNull
    public final String email;
    public final boolean deleted;

    public UserTO(Long id, String name, String email) {
        this(id, name, email, false);
    }

    @JsonCreator
    public UserTO(Long id, String name, String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
    }

    public static UserTO fromEntity(UserEntity entity) {
        return new UserTO(entity.getId(), entity.getName(), entity.getEmail(), entity.isDeleted());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserTO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTO user = (UserTO) o;
        return deleted == user.deleted && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, deleted);
    }
}
