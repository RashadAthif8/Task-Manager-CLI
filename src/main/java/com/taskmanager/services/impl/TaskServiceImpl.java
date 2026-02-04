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

    // Cria uma nova tarefa
    @Override
    public Task createTask(String title, String description, LocalDate dueDate) {
        // 1. Validação de Negócio (Guard Clauses)
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty"); 
        }

        if (dueDate != null && dueDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Due Date cannot be in the past"); 
        }

        // 2. Criação do Objeto
        // Dica: Se o seu construtor aceitar o ID, melhor. 
        // Caso contrário, o setId está correto após a criação.
        Task newTask = new Task(title, description, dueDate);
        newTask.setId(idCounter++);

        tasks.add(newTask);
        // TODO: Integrar método saveTask() do TaskRepository aqui
        taskRepository.saveTask(newTask);
        return newTask;

    }

    // Lista todas as tarefas
    @Override
    public List<Task> getAllTasks() {
        // TODO: Integrar método findAll() do TaskRepository aqui
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

    // Listar Tarefas por Id
    public Task findByIdLong(Long id) {
        // TODO: Integrar método findById() do TaskRepository aqui
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

    // Este é o "atalho" para você não precisar digitar o L
    public Task findById(int id) {
        return findByIdLong((long) id); // Converte o int para Long e chama o método acima
    }

    // Deletar Tarefas por Id
    public void deleteTaskLong(Long id) {
        // TODO: Integrar método deleteById() do TaskRepository aqui
        boolean deletedFromDb = taskRepository.deleteById(id);

        // removeIf retorna true se encontrou e removeu algo
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        
        if (deletedFromDb && removed) {
            System.out.println("Task with Id " + id + " was successfully deleted!");
        } else {
            System.out.println("Task with Id " + id + " not found.");
        }
    }

    // Atualizar Tarefas
    public void updateTask(Task task) {
        Task existingTask = findByIdLong(task.getId());

        if (existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setStatus(task.getStatus());
            // TODO: Integrar método update() do TaskRepository aqui
            taskRepository.update(existingTask);
        }

        System.out.println("Task with Id " + task.getId() + " was updated.");
    }

    // Deletar Tarefas
    public void deleteTask(int id) {
        deleteTaskLong((long) id);
    }

    // ========== Métodos de Marcação de Status ==========

    // Marcar Tarefas como DONE
    public void markAsDoneLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.DONE);
            // TODO: Integrar método update() do TaskRepository aqui (para atualizar status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as done.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Atalhos para int
    public void markAsDone(int id) {
        markAsDoneLong((long) id);
    }

    // Marcar Tarefas como WIP
    public void markAsWIPLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.WIP);
            // TODO: Integrar método update() do TaskRepository aqui (para atualizar status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as in progress.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Atalhos para int
    public void markAsWIP(int id) {
        markAsWIPLong((long) id);
    }

    // Marcar Tarefas como TO_DO
    public void markAsToDoLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.TO_DO);
            // TODO: Integrar método update() do TaskRepository aqui (para atualizar status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as to do.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Atalhos para int
    public void markAsToDo(int id) {
        markAsToDoLong((long) id);
    }

    // Marcar Tarefas como CANCELLED
    public void markAsCancelledLong(Long id) {
        Task task = findByIdLong(id);
        
        if (task != null) {
            task.setStatus(StatusEnum.CANCELLED);
            // TODO: Integrar método update() do TaskRepository aqui (para atualizar status)
            taskRepository.update(task);
            System.out.println("Task with Id " + id + " marked as cancelled.");
        } else {
            System.out.println("Error: Could not find task with Id " + id);
        }
    }

    // Atalhos para int
    public void markAsCancelled(int id) {
        markAsCancelledLong((long) id);
    }


}
