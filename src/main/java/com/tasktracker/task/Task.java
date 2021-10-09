package com.tasktracker.task;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Task {
    private Long id;
    @NotEmpty(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    @NotNull(message = "Reporter is mandatory")
    private Long reporter;
    private Long assignee;

    @JsonCreator
    public Task(Long id, String title, String description, Long reporter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReporter() {
        return reporter;
    }

    public void setReporter(Long reporter) {
        this.reporter = reporter;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", reporter='").append(reporter).append('\'');
        sb.append(", assignee='").append(assignee).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
