package com.tasktracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private TaskRepository repository;

    private Path testFile;

    @BeforeEach
    void setUp() {
        testFile = Path.of("testFile.json");
        repository = new TaskRepository(testFile);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(testFile);
    }

    @Test
    void addTask() throws Exception {
        int id = repository.addTask("Random");
        assertEquals(1, id);

        String json = repository.getTasks();
        assertTrue(json.contains("Random"));
    }

    @Test
    void updateTask_success() throws Exception {
        int id = repository.addTask("Old description");
        String json = repository.getTasks();
        assertTrue(json.contains("Old description"));

        boolean updated = repository.updateTask(id, "New description");
        assertTrue(updated);

        json = repository.getTasks();
        assertTrue(json.contains("New description"));
        assertFalse(json.contains("Old description"));
    }

    @Test
    void updateTask_fail() {
        int id = 10;

        boolean updated = repository.updateTask(id, "New description");
        assertFalse(updated);
    }

    @Test
    void deleteTask_success() throws Exception {
        int id = repository.addTask("Task to delete");
        String json = repository.getTasks();
        assertTrue(json.contains("Task to delete"));

        boolean deleted = repository.deleteTask(id);
        assertTrue(deleted);

        json = repository.getTasks();
        assertFalse(json.contains("Task to delete"));
    }

    @Test
    void deleteTask_fail() {
        int id = 10;

        boolean deleted = repository.deleteTask(id);
        assertFalse(deleted);
    }

    @Test
    void markInProgress_success() throws Exception {
        int id = repository.addTask("Task in progress");
        String json = repository.getTasks();
        assertTrue(json.contains("Task in progress"));

        boolean marked = repository.markInProgress(id);
        assertTrue(marked);

        json = repository.getTasksByStatus(Status.IN_PROGRESS);
        assertTrue(json.contains("Task in progress"));
    }

    @Test
    void markInProgress_fail() {
        int id = 10;

        boolean marked = repository.markInProgress(id);
        assertFalse(marked);
    }

    @Test
    void markDone_success() throws Exception {
        int id = repository.addTask("Task done");
        String json = repository.getTasks();
        assertTrue(json.contains("Task done"));

        boolean marked = repository.markDone(id);
        assertTrue(marked);

        json = repository.getTasksByStatus(Status.DONE);
        assertFalse(json.contains("Task in progress"));
    }

    @Test
    void markDone_fail() {
        int id = 10;

        boolean marked = repository.markDone(id);
        assertFalse(marked);
    }

}