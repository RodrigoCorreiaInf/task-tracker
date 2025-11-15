package com.tasktracker;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.file.Path;

/**
 * Service layer connecting the CLI and the task repository.
 * <p>
 * Wraps repository operations and prints result outputs.
 * </p>
 */
public class TaskService {

    private final TaskRepository repository;

    /**
     * Creates a new TaskService using the provided repository file path.
     *
     * @param repoFilePath path to the JSON file containing tasks
     */
    public TaskService(Path repoFilePath) {
        repository = new TaskRepository(repoFilePath);
    }

    /**
     * Adds a task.
     *
     * @param description task description
     * @return created task ID
     */
    public int addTask(String description) {
        return repository.addTask(description);
    }

    /**
     * Updates a task.
     *
     * @param id          task ID
     * @param description new description
     * @return {@code true} if updated successfully
     */
    public boolean updateTask(int id, String description) {
        return repository.updateTask(id, description);
    }

    /**
     * Deletes a task.
     *
     * @param id task ID
     * @return {@code true} if deleted successfully
     */
    public boolean deleteTask(int id) {
        return repository.deleteTask(id);
    }

    /**
     * Marks a task as in progress.
     *
     * @param id task ID
     * @return {@code true} if updated successfully
     */
    public boolean markInProgress(int id) {
        return repository.markInProgress(id);
    }

    /**
     * Marks a task as done.
     *
     * @param id task ID
     * @return {@code true} if updated successfully
     */
    public boolean markDone(int id) {
        return repository.markDone(id);
    }

    /**
     * Prints all tasks.
     *
     * @throws JsonProcessingException if JSON parsing fails
     */
    public void listAll() throws JsonProcessingException {
        String tasks = repository.getTasks();
        System.out.println(tasks);
    }

    /**
     * Prints tasks with status {@link Status#DONE}.
     *
     * @throws JsonProcessingException if JSON parsing fails
     */
    public void listDone() throws JsonProcessingException {
        String doneTasks = repository.getTasksByStatus(Status.DONE);
        System.out.println(doneTasks);
    }

    /**
     * Prints tasks with status {@link Status#TODO}.
     *
     * @throws JsonProcessingException if JSON parsing fails
     */
    public void listTodo() throws JsonProcessingException {
        String todoTasks = repository.getTasksByStatus(Status.TODO);
        System.out.println(todoTasks);
    }

    /**
     * Prints tasks with status {@link Status#IN_PROGRESS}.
     *
     * @throws JsonProcessingException if JSON parsing fails
     */
    public void listInProgress() throws JsonProcessingException {
        String inProgressTasks = repository.getTasksByStatus(Status.IN_PROGRESS);
        System.out.println(inProgressTasks);
    }


}

