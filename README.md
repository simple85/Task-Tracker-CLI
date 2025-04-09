# Task Tracker CLI

This is a simple command-line interface (CLI) application for managing tasks. You can add, update, delete, mark, and list tasks directly from the terminal.

## Features

- Add a Task: Add a new task with a description.
- Update a Task: Update the description of an existing task.
- Delete a Task: Remove a task by its ID.
- Mark a Task: Mark a task as "in progress" or "done."
- List Tasks: List all tasks or filter them by status (e.g., to-do, in progress, done).
- List commands: List all commands available to use

## Installation

1. **Clone the repository:**
  
```bash
git clone https://github.com/simple85/Task-Tracker-CLI.git
cd Task-Tarcker-CLI/src/main/java
```
2. **Compile the source code:**

```
javac Task.java TaskManager.java TaskTrackerCLI.java
```
3. **Run the application:**

```bash
java TaskCLIApp <command> [arguments]
```

## Usage
```
# Adding a new task
java TaskTrackerCLI add "Buy groceries"
# Output: Task created (ID: 1)

# Updating a task
java TaskTrackerCLI update 1 "Buy groceries and cook dinner"
# Output: Task updated (ID: 1)

# Deleting a task
java TaskTrackerCLI delete 1
# Output: Task deleted (ID: 1)

# Marking a task as in progress
java TaskTrackerCLI mark-in-progress 1
# Output: Task marked as: in progress (ID: 1)

# Marking a task as done
java TaskTrackerCLI mark-done 1
# Output: Task marked as: done (ID: 1)

# Listing all tasks
java TaskTrackerCLI list
# Output: List of all tasks

# Listing tasks by status
java TaskTrackerCLI list to-do
java TaskTrackerCLI list in-progress
java TaskTrackerCLI list done

# Listing executable commands
java TaskTrackerCLI /help
```

This project is created following the guideline of: https://roadmap.sh/projects/task-tracker

