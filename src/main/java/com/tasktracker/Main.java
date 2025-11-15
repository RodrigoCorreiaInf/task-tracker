package com.tasktracker;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.file.Path;

/**
 * Entry point for the Task Tracker CLI application.
 * <p>
 * Supports commands such as adding, updating, deleting,
 * and listing tasks, as well as marking tasks with different statuses.
 * </p>
 */
public class Main {

    /**
     * Application entry point. Parses CLI arguments and executes commands.
     *
     * @param args command-line arguments
     *
     * @throws JsonProcessingException if JSON parsing or serialization fails
     */
    public static void main(String[] args) throws JsonProcessingException {
        Path repoFilePath = Path.of("tasks.json");
        TaskService taskService = new TaskService(repoFilePath);
        String command = args[0];

        switch (command) {
            case "add" -> {
                if (args.length != 2) {
                    System.out.println("Usage: task-cli add \"Task description\"");
                    return;
                }

                String description = args[1];
                int taskId = taskService.addTask(description);

                if (taskId != -1) {
                    System.out.println("Task added successfully (ID: " + taskId + ")");
                } else {
                    System.out.println("Error adding task.");
                }
            }

            case "update" -> {
                if (args.length != 3) {
                    System.out.println("Usage: task-cli update <id> \"New description\"");
                    return;
                }

                int id = Integer.parseInt(args[1]);
                String description = args[2];
                if (taskService.updateTask(id, description)) {
                    System.out.println("Task updated.");
                } else {
                    System.out.println("Task not found.");
                }
            }

            case "delete" -> {
                if (args.length != 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                }

                int id = Integer.parseInt(args[1]);

                if (taskService.deleteTask(id)) {
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Task not found.");
                }
            }

            case "mark-in-progress" -> {
                if (args.length != 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }

                int id = Integer.parseInt(args[1]);

                if (taskService.markInProgress(id)) {
                    System.out.println("Task marked as in progress.");
                } else {
                    System.out.println("Task not found.");
                }
            }

            case "mark-done" -> {
                if (args.length != 2) {
                    System.out.println("Usage: task-cli mark-done <id>");
                    return;
                }

                int id = Integer.parseInt(args[1]);

                if (taskService.markDone(id)) {
                    System.out.println("Task marked as done.");
                } else {
                    System.out.println("Task not found.");
                }

            }

            case "list" -> {
                if (args.length == 1) {
                    taskService.listAll();
                } else {
                    listByStatus(args[1], taskService);
                }
            }

            default -> printHelp();
        }

    }

    /**
     * Lists tasks filtered by the provided status string.
     *
     * @param status      the status filter ("done", "todo", "in-progress")
     * @param taskService service used to access task operations
     * @throws JsonProcessingException if JSON parsing fails
     */
    private static void listByStatus(String status, TaskService taskService) throws JsonProcessingException {
        switch (status) {
            case "done" -> taskService.listDone();
            case "todo" -> taskService.listTodo();
            case "in-progress" -> taskService.listInProgress();
            default -> System.out.println("Unknown status: " + status);
        }
    }

    /**
     * Prints the help menu showing supported CLI commands.
     */
    private static void printHelp() {
        System.out.println("""
                Usage:
                  task-cli add "Task description"
                  task-cli update <id> "New description"
                  task-cli delete <id>
                  task-cli mark-in-progress <id>
                  task-cli mark-done <id>
                  task-cli list
                  task-cli list done
                  task-cli list todo
                  task-cli list in-progress
                """);
    }

}