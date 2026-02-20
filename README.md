# Task Tracker CLI

[![CI](https://github.com/simple85/Task-Tracker-CLI/actions/workflows/ci.yml/badge.svg)](https://github.com/simple85/Task-Tracker-CLI/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/build-Maven-C71A36?logo=apachemaven)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A lightweight command-line task manager written in plain Java. Tasks are stored as JSON in a local `tasks.json` file, so your list survives between runs without any database.

Built as a solution to the [roadmap.sh Task Tracker](https://roadmap.sh/projects/task-tracker) project.

## Features

- **Add, update and delete** tasks by ID
- **Track progress** — mark tasks as `in-progress` or `done`
- **Filter** the list by status (`to-do`, `in-progress`, `done`, `all`)
- **Persistent storage** in a human-readable JSON file
- **Timestamps** for creation and last update on every task

## Tech Stack

| Area | Tools |
|------|-------|
| Language | Java 21 |
| Build | Maven, Maven Shade (runnable fat jar) |
| JSON | [org.json](https://github.com/stleary/JSON-java) |
| Testing | JUnit 5, AssertJ, JaCoCo coverage |
| CI | GitHub Actions |

## Getting Started

**Prerequisites:** JDK 21+ and Maven 3.9+

```bash
git clone https://github.com/simple85/Task-Tracker-CLI.git
cd Task-Tracker-CLI
mvn package
```

This produces a self-contained jar at `target/task-cli.jar`.

## Usage

```bash
java -jar target/task-cli.jar <command> [arguments]
```

| Command | Description |
|---------|-------------|
| `add "<description>"` | Create a new task |
| `update <id> "<description>"` | Change a task's description |
| `delete <id>` | Remove a task |
| `mark-in-progress <id>` | Set status to `in-progress` |
| `mark-done <id>` | Set status to `done` |
| `list [status]` | List all tasks, or only those with the given status |
| `/help` | Show all commands |

### Example

```text
$ java -jar target/task-cli.jar add "Buy groceries"
Task created (ID: 1)

$ java -jar target/task-cli.jar mark-in-progress 1
Task marked as: in-progress (ID: 1)

$ java -jar target/task-cli.jar list in-progress
ID: 1 | STATUS: in-progress | TASK: Buy groceries | UPDATED: 2026-01-12T10:31:04 | CREATED: 2026-01-12T10:30:12
```

Tasks are saved to `tasks.json` in the directory you run the command from.

## Project Structure

```text
src/
├── main/java/
│   ├── TaskTrackerCLI.java   # entry point, argument parsing
│   ├── TaskManager.java      # CRUD operations and JSON persistence
│   └── Task.java             # task model + JSON (de)serialization
└── test/java/
    ├── TaskManagerTest.java
    └── TaskTest.java
```

## Running Tests

```bash
mvn verify
```

The coverage report is written to `target/site/jacoco/index.html`.

## License

Released under the [MIT License](LICENSE).
