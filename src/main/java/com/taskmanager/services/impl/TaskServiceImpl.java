package com.taskmanager.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.taskmanager.model.Task;
import com.taskmanager.model.enums.StatusEnum;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.services.TaskService;

public class TaskServiceImpl implements TaskService {
    private final List<Task> tasks = new ArrayList<>(); 
    private Long idCounter = 1L;
    private final TaskRepository taskRepository = new TaskRepository();

    // Create a new task
    @Override
    public Task createTask(String title, String description, LocalDate dueDate) {
        // 1. Business Validation (Guard Clauses)
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty"); 
        }

        if (dueDate != null && dueDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Due Date cannot be in the past"); 
        }

        // 2. Object Creation
        // If your constructor accepts the ID, that's better.
        // Otherwise, setId is correct after creation.
        Task newTask = new Task(title, description, dueDate);
        newTask.setId(idCounter++);

        tasks.add(newTask);
        // TODO: Integrate saveTask() method from TaskRepository here
        taskRepository.saveTask(newTask);
        return newTask;

    }

    // List all tasks
    @Override
    public List<Task> getAllTasks() {
        //Integrate findAll() method from TaskRepository here
        List<Task> tasksFromDb = taskRepository.findAll();
        List<Task> sortedTasks = tasksFromDb.stream()
                .sorted(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
        
        System.out.println("========== Task List ==========\n");
        for (Task task : sortedTasks) {
            task.showStatus();
            System.out.println();
        }

        return sortedTasks;
    }

    // List Tasks by Id
    public Task findByIdLong(Long id) {
        // Integrate findById() method from TaskRepository here
        Task taskFromDb = taskRepository.findById(id);
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                System.out.println("---> Task found <---"); // Task
                task.showStatus();
                return taskFromDb;
            }
        }
        System.out.println("Task with Id " + id + " not found.");
        return null;
    }

    // This is the "shortcut" so you don't have to type the L
    public Task findById(int id) {
        return findByIdLong((long) id); // Converts int to Long and calls the method above
    }

    // Delete Tasks by Id
    public void deleteTaskLong(Long id) {
        // Integrate deleteById() method from TaskRepository here
        boolean deletedFromDb = taskRepository.deleteById(id);

        // removeIf returns true if it found and removed something
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        
        if (deletedFromDb && removed) {
            System.out.println("Task with Id " + id + " was successfully deleted!");
        } else {
            System.out.println("Task with Id " + id + " not found.");
        }
    }

    public void updateTask(Task task) {
        Task existingTask = findByIdLong(task.getId());

        if (existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setStatus(task.getStatus());
            taskRepository.update(existingTask);
        }

        System.out.println("Task with Id " + task.getId() + " was updated.");
    }

    public void deleteTask(int id) {
        deleteTaskLong((long) id);
    }


    public void markAsDoneLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.DONE);
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as done.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    public void markAsDone(int id) {
        markAsDoneLong((long) id);
    }

    // Mark Tasks as WIP
    public void markAsWIPLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.WIP);
            // Integrate update() method from TaskRepository here (to update status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as in progress.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Shortcuts for int
    public void markAsWIP(int id) {
        markAsWIPLong((long) id);
    }

    // Mark Tasks 
    public void markAsToDoLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.TO_DO);
            // Integrate update() method from TaskRepository here (to update status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as to do.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Shortcuts for int
    public void markAsToDo(int id) {
        markAsToDoLong((long) id);
    }

    // Mark Tasks as CANCELLED
    public void markAsCancelledLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.CANCELLED);
            // Integrate update() method from TaskRepository here (to update status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as cancelled.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Shortcuts for int
    public void markAsCancelled(int id) {
        markAsCancelledLong((long) id);
    }


}
