# ☑️ Task Manager CLI

A command-line task manager built with Java and Maven. Organize your tasks with simplicity.

## 🚀 Quick Start

Clone and run:

```bash
# Clone the repository
git clone https://github.com/RashadAthif8/Task-Manager-CLI

# Enter the directory
cd taskmanager-cli

# Compile with Maven
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.taskmanager.App"
```

---

## ✨ Features

- **Interactive CLI Menu** - Intuitive menu-driven interface
- **Create Tasks** - Add tasks with title, description, and due date
- **List Tasks** - View all tasks sorted by urgency
- **Search Tasks** - Find tasks by ID
- **Update Tasks** - Modify task details
- **Update Status** - Change task status (To Do, In Progress, Done, Cancelled)
- **Delete Tasks** - Remove tasks
- **Automatic Overdue Detection** - Identifies overdue tasks
- **Data Persistence** - SQLite database for permanent storage
- **100% Offline** - All data stored locally on your machine

---

## 📋 Available Operations

| Operation | Description |
|-----------|-------------|
| Create Task | Create new task with validation |
| List All Tasks | Display tasks sorted by due date |
| Find Task by ID | Search for a specific task |
| Update Task | Modify task details |
| Update Task Status | Change task status |
| Delete Task | Remove a task |

---

## 🏗️ Architecture

The project follows a modular structure with clear separation of concerns:

```
src/main/java/com/taskmanager/
├── App.java                    # Entry point
├── config/
│   └── DatabaseConfig.java     # SQLite configuration
├── model/
│   ├── Task.java              # Task entity
│   └── enums/
│       └── StatusEnum.java    # Task status enum
├── repository/
│   └── TaskRepository.java    # Data access layer
├── services/
│   ├── TaskService.java       # Service interface
│   └── impl/
│       └── TaskServiceImpl.java # Service implementation
└── ui/
    └── UserInterface.java     # CLI interface
```

---

## 🔧 Development

```bash
# Compile
mvn clean compile

# Run
mvn exec:java -Dexec.mainClass="com.taskmanager.App"

# Run tests
mvn test

# Build JAR
mvn package
```

**Requirements:**
- Java 17+
- Maven 3.6+
- Lombok 1.18.32
- SQLite JDBC 3.45.1.0
- JUnit Jupiter 5.10.1

---

## 🤝 Contributing

Contributions are welcome! Feel free to open Pull Requests.

1. **Fork** the repository
2. **Create** your feature branch (`git checkout -b feature/new-feature`)
3. **Commit** your changes (`git commit -m 'Add new feature'`)
4. **Push** to the branch (`git push origin feature/new-feature`)
5. **Open** a Pull Request

---

## 📄 License

This project is licensed under GPLv3 — see the LICENSE file for details.
# Task-Manager-CLI
# Task-Manager-CLI
