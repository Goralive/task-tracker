package com.tasktracker.service.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tasktracker.repository.entity.TaskEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TaskTO {
    public final Long id;
    @NotEmpty(message = "Title is mandatory")
    public final String title;
    @NotEmpty(message = "Description is mandatory")
    public final String description;
    @NotNull(message = "Reporter is mandatory")
    public final Long reporter;
    public final Long assignee;

    @JsonCreator
    public TaskTO(Long id, String title, String description, Long reporter, Long assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskEntity{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", reporter='").append(reporter).append('\'');
        sb.append(", assignee='").append(assignee).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static TaskTO fromEntity(TaskEntity taskEntity) {
        return new TaskTO(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(),
                taskEntity.getReporter(), taskEntity.getAssignee());
    }
}

