package com.tasktracker.repository.entity;

public class TaskEntity {
    private Long id;
    private String title;
    private String description;
    private Long reporter;
    private Long assignee;

    public TaskEntity(Long id, String title, String description, Long reporter, Long assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
    }

    public TaskEntity(Long id, String title, String description, Long reporter) {
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

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskEntity{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", reporter=").append(reporter);
        sb.append(", assignee=").append(assignee);
        sb.append('}');
        return sb.toString();
    }
}
