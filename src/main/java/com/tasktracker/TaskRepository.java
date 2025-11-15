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

public class TaskRepository {

    private final ObjectMapper mapper = new ObjectMapper();

    private HashMap<Integer, Task> tasks;

    private final Path filePath;

    public TaskRepository(Path filePath) {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        tasks = new HashMap<>();
        this.filePath = filePath;
    }

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

    private void serialize() throws IOException {
        mapper.writeValue(new File(String.valueOf(filePath)), tasks.values());
    }

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
