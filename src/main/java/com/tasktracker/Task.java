package com.tasktracker;

import java.time.LocalDateTime;

/**
 * Represents a single task in the task tracker system.
 * <p>
 * Stores task metadata such as ID, description, status,
 * creation timestamp, and last update timestamp.
 * </p>
 */
public class Task {

    private int id;

    private String description;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Creates an empty Task instance (used for deserialization).
     */
    public Task() {
    }

    /**
     * Creates a new task with an ID and description.
     * The task is initialized with status {@link Status#TODO}.
     *
     * @param id          numeric identifier for the task
     * @param description short description of the task
     */
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        status = Status.TODO;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * @return the task ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the current task status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return timestamp when the task was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return timestamp when the task was last updated
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Updates the task description and refreshes the last updated timestamp.
     *
     * @param description new description text
     */
    public void setDescription(String description) {
        this.description = description;
        setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Updates the task status and refreshes the last updated timestamp.
     *
     * @param status new status for the task
     */
    public void setStatus(Status status) {
        this.status = status;
        setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Sets the last updated timestamp.
     *
     * @param updatedAt new update timestamp
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return formatted string containing task details
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
