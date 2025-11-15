package com.tasktracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Repository class handling persistence and retrieval of tasks.
 * <p>
 * Tasks are stored in a JSON file and loaded using Jackson.
 * The repository provides CRUD operations and filtering by status.
 * </p>
 */
public class TaskRepository {

    private final ObjectMapper mapper = new ObjectMapper();

    private HashMap<Integer, Task> tasks;

    private final Path filePath;

    /**
     * Creates a new repository bound to the specified JSON file.
     *
     * @param filePath path to the JSON file used to store tasks
     */
    public TaskRepository(Path filePath) {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        tasks = new HashMap<>();
        this.filePath = filePath;
    }

    /**
     * Adds a new task to the repository.
     *
     * @param description description of the new task
     *
     * @return generated task ID, or -1 if an error occurs
     */
    public int addTask(String description) {
        try {
            deserialize();
            int id = tasks.keySet().stream().max(Integer::compare).orElse(0) + 1;
            Task task = new Task(id, description);
            tasks.put(id, task);
            serialize();
            return task.getId();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return -1;
    }

    /**
     * Updates the description of an existing task.
     *
     * @param id          task ID
     * @param description new task description
     *
     * @return {@code true} if the task exists and was updated; {@code false} otherwise
     */
    public boolean updateTask(int id, String description) {
        try {
            deserialize();
            if (!tasks.containsKey(id)) {
                return false;
            }
            Task task = tasks.get(id);
            task.setDescription(description);
            serialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return true;
    }

    /**
     * Deletes a task by ID.
     *
     * @param id task ID
     *
     * @return {@code true} if successfully deleted; {@code false} if the task does not exist
     */
    public boolean deleteTask(int id) {
        try {
            deserialize();
            if (!tasks.containsKey(id)) {
                return false;
            }
            tasks.remove(id);
            serialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return true;
    }

    /**
     * Marks a task as {@link Status#IN_PROGRESS}.
     *
     * @param id task ID
     *
     * @return {@code true} if updated; {@code false} otherwise
     */
    public boolean markInProgress(int id) {
        try {
            deserialize();
            if (!tasks.containsKey(id)) {
                return false;
            }
            Task task = tasks.get(id);
            task.setStatus(Status.IN_PROGRESS);
            serialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return true;
    }

    /**
     * Marks a task as {@link Status#DONE}.
     *
     * @param id task ID
     *
     * @return {@code true} if updated; {@code false} otherwise
     */
    public boolean markDone(int id) {
        try {
            deserialize();
            if (!tasks.containsKey(id)) {
                return false;
            }
            Task task = tasks.get(id);
            task.setStatus(Status.DONE);
            serialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return true;
    }

    /**
     * Retrieves all tasks stored in the repository.
     *
     * @return JSON string containing all tasks, or "No tasks." if empty
     *
     * @throws JsonProcessingException if serialization fails
     */
    public String getTasks() throws JsonProcessingException {
        try {
            deserialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        if (!tasks.isEmpty()) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tasks.values());
        } else {
            return "No tasks.";
        }
    }

    /**
     * Retrieves all tasks matching the given status.
     *
     * @param status status filter
     *
     * @return JSON array containing the selected tasks,
     * or a message indicating no matching tasks
     *
     * @throws JsonProcessingException if serialization fails
     */
    public String getTasksByStatus(Status status) throws JsonProcessingException {
        try {
            deserialize();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        List<Task> taskList = new ArrayList<>(tasks.values());
        List<Task> statusList = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getStatus().equals(status)) {
                statusList.add(task);
            }
        }

        if (!statusList.isEmpty()) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(statusList);
        } else {
            return "No task with status " + status + ".";
        }
    }

    /**
     * Serializes the task collection to disk.
     *
     * @throws IOException if writing to the file fails
     */
    private void serialize() throws IOException {
        mapper.writeValue(new File(String.valueOf(filePath)), tasks.values());
    }
    
    /**
     * Loads tasks from disk into memory.
     *
     * @throws IOException if file reading fails
     */
    private void deserialize() throws IOException {
        TypeReference<List<Task>> typeReference = new TypeReference<>() {
        };

        if (Files.exists(filePath)) {
            List<Task> taskList = mapper.readValue(new File(String.valueOf(filePath)), typeReference);

            tasks = new HashMap<>();
            for (Task task : taskList) {
                tasks.put(task.getId(), task);
            }
        }

    }

}
