# Task Tracker CLI

A simple command-line **Task Tracker** built in Java 21.  
Supports adding, updating, deleting, listing, and changing the status of tasks stored in a JSON file.

Project from roadmap.sh: https://roadmap.sh/projects/task-tracker
Ideal for learning Java, Maven, JSON serialization (Jackson), and CLI app distribution.

---

## Features

- Add tasks
- Update tasks
- Delete tasks
- List all tasks
- List tasks by status (`todo`, `in-progress`, `done`)
- Mark tasks as *in progress* or *done*
- JSON-based persistence
- Distributed as a **fat JAR** (`jar-with-dependencies`)
- Includes a **task-cli.bat** script for easy execution on Windows

---

## Requirements

- **Java 21** or higher
- **Maven 3.8+**

---

## Project Structure

```
task-tracker/
 ├── src/
 │   └── main/java/com/tasktracker/...
 ├── tasks.json               # Created automatically when tasks are added
 ├── task-cli.bat             # Windows helper script
 ├── pom.xml
 └── README.md
```

---

## Build the project

Run:

```sh
mvn clean package
```

This will generate the fat JAR:

```
target/task-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## Running the CLI

### **Option 1 — Using the .bat script (Windows)**

```
task-cli.bat add "My task" 
task-cli.bat list
task-cli.bat update 1 "New description"
task-cli.bat delete 2
task-cli.bat mark-in-progress 3
task-cli.bat mark-done 3
task-cli.bat list done
```

The script simply runs:

```bat
java -jar "target\task-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar" %*
```

---

### **Option 2 — Running manually**

```sh
java -jar target/task-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar <command> [args]
```

Examples:

```sh
java -jar target/task-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar add "Buy milk"
java -jar target/task-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar list
```

---

## 🧾 Available Commands

| Command | Description |
|--------|-------------|
| `add "description"` | Add a new task |
| `update <id> "new description"` | Update the text of a task |
| `delete <id>` | Remove a task |
| `mark-in-progress <id>` | Mark task as IN_PROGRESS |
| `mark-done <id>` | Mark task as DONE |
| `list` | Show all tasks |
| `list todo` | Show only TODO tasks |
| `list in-progress` | Show only IN_PROGRESS tasks |
| `list done` | Show only DONE tasks |

---

## Where are tasks stored?

A file named `tasks.json` will be created in the project root:

Example content:

```json
[
  {
    "id": 1,
    "description": "Example task",
    "status": "TODO",
    "createdAt": "2025-01-01T12:00:00",
    "updatedAt": "2025-01-01T12:00:00"
  }
]
```

---

## Running Tests

```sh
mvn test
```

---

## Technologies Used

- Java 21
- Maven
- Jackson (Databind + JSR310)
- JUnit 5

---

## Contributing

Feel free to open issues or submit pull requests.

---

## 📄 License

This project is provided for educational purposes — use freely.

