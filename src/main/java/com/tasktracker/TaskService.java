package com.tasktracker;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.file.Path;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(Path repoFilePath) {
        repository = new TaskRepository(repoFilePath);
    }

    public int addTask(String description) {
        return repository.addTask(description);
    }

    public boolean updateTask(int id, String description) {
        return repository.updateTask(id, description);
    }

    public boolean deleteTask(int id) {
        return repository.deleteTask(id);
    }

    public boolean markInProgress(int id) {
        return repository.markInProgress(id);
    }

    public boolean markDone(int id) {
        return repository.markDone(id);
    }

    public void listAll() throws JsonProcessingException {
        String tasks = repository.getTasks();
        System.out.println(tasks);
    }

    public void listDone() throws JsonProcessingException {
        String doneTasks = repository.getTasksByStatus(Status.DONE);
        System.out.println(doneTasks);
    }

    public void listTodo() throws JsonProcessingException {
        String todoTasks = repository.getTasksByStatus(Status.TODO);
        System.out.println(todoTasks);
    }

    public void listInProgress() throws JsonProcessingException {
        String inProgressTasks = repository.getTasksByStatus(Status.IN_PROGRESS);
        System.out.println(inProgressTasks);
    }


}

