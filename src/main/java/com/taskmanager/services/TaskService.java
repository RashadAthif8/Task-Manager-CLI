package com.taskmanager.services;

import com.taskmanager.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task createTask(String title, String description, LocalDate dueDate);
    List<Task> getAllTasks();
    Task findById(int id);
    void updateTask(Task task);
    void deleteTask(int id);
    void markAsDone(int id);
    void markAsWIP(int id);
    void markAsToDo(int id);
    void markAsCancelled(int id);
}
