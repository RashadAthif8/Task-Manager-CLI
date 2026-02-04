package com.taskmanager.ui;

import java.time.LocalDate;
import java.util.Scanner;

import com.taskmanager.model.Task;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.impl.TaskServiceImpl;

public class UserInterface {
    private Scanner scanner;
    private TaskService taskService;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.taskService = new TaskServiceImpl();
    }

    public void start() {
        System.out.println("Welcome to the Task Manager!");

        // Implement the user interface logic here
        boolean running = true;

        while (running) {
            menuTaskManager();
            int option = readOption();

            switch (option) {
                case 1:
                    createTaskFlow();
                    break;
                case 2:
                    // No specific UI method needed to list tasks
                    taskService.getAllTasks();
                    break;
                case 3:
                    // No specific UI method needed to find task by Id
                    System.out.print("Task Id to find a task: ");
                    int idFind = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    taskService.findById(idFind);
                    break;
                case 4:
                    System.out.print("Task Id to update: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    updateTaskFlow(idUpdate);
                    break;
                case 5:
                    System.out.print("Task Id to update status: ");
                    int idStatus = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    updateStatusFlow(idStatus);
                    break;
                case 6:
                    // No specific UI method needed to delete task
                    System.out.print("Task Id to delete a task: ");
                    int idDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    taskService.deleteTask(idDelete);
                    break;
                case 0:
                    running = false;
                    System.out.println();
                    System.out.println("Exiting Task Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;

            }
            
        }

    scanner.close();

    }

    // DONE: IMPLEMENT THE readOption() METHOD HERE
    // This method should read the user's option and return an integer
    private int readOption() {
        try {
            System.out.print("\nSelect an option: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // DONE: IMPLEMENT THE createTaskFlow() METHOD HERE
    // This method should guide the user through the process of creating a new task
    private void createTaskFlow() {
        System.out.println("\n--- New Task ---");

        // 1. Reading Simple Text (Title)
        System.out.print("Title: ");
        String title = scanner.nextLine();

        // 2. Reading Long Text (Description)
        System.out.print("Description: ");
        String description = scanner.nextLine();

        // 3. Reading Date (Due Date)
        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDateInput = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateInput);

        taskService.createTask(title, description, dueDate);
        System.out.println("Task created successfully!\n");
        
    }

    // TO DO: IMPLEMENT THE updateTaskFlow() METHOD HERE
    // This method should guide the user through the process of updating a task
    private void updateTaskFlow(int taskId) {
        System.out.println("\n--- Update Task ---");
        // 1. Check if the task exists before asking anything
        Task task = taskService.findById(taskId);

        if (task == null) {
            System.out.println("Task with Id " + taskId + " not found.");
            return;
        }  

        // 2. Show the current task data
        System.out.println("\n--- Updating Task " + taskId + " ---");
        System.out.println("Current Data: " + task.getTitle() + "\n" + task.getDescription() + "\n" + task.getDueDate());

        // 2. Collect the new data
        System.out.print("New Title: ");
        String newTitle = scanner.nextLine();

        System.out.print("New Descriptin: ");
        String newDescription = scanner.nextLine();

        System.out.print("New Due Date (YYYY-MM-DD): ");
        LocalDate newDueDate = LocalDate.parse(scanner.nextLine());

        // 3. Create a temporary Task object with the new data
        // IMPORTANT: The ID must be the same as the task we want to change
        Task taskAtualizada = new Task(newTitle, newDescription, newDueDate);
        taskAtualizada.setId((long) taskId); // The ID that came from the parameter
        taskAtualizada.setStatus(task.getStatus()); // Keep the current status

        // 4. Send to the Service to make the replacement
        taskService.updateTask(taskAtualizada);
    }

    // TO DO: IMPLEMENT THE METHOD TO UPDATE TASK STATUS HERE
    // This method should guide the user through the process of updating a task's status
   private void updateStatusFlow(int taskId) {
        System.out.println("\n--- Update Task Status ---");

        // 1. Check if the task exists before asking anything
        Task task = taskService.findById(taskId);
        if (task == null) {
            System.out.println("Task with Id " + taskId + " not found.");
            return;
        } 

        // 2. Show the current status of the task
        System.out.println("Current Status: " + task.getStatus());

        // 3. Collect the new status
        System.out.print("New Status (DONE, IN_PROGRESS, TO DO, CANCELLED): ");
        String newStatusInput = scanner.nextLine();
        
        // 4. Update the task status based on user input
        if (newStatusInput.equals("DONE")) {
            taskService.markAsDone(taskId);
        } else if ( newStatusInput.equals("IN_PROGRESS")) {
            taskService.markAsWIP(taskId);
        } else if (newStatusInput.equals("TO DO")) {
            taskService.markAsToDo(taskId);
        } else if (newStatusInput.equals("CANCELLED")) {
            taskService.markAsCancelled(taskId);
        } else {
            System.out.println("Invalid status input.");
        }
   }

    // DONE: IMPLEMENT THE menuTaskManager() METHOD HERE
    // This method should display the main menu of the task manager
    private void menuTaskManager() {
        System.out.println("\n--- Task Manager Menu ---");
        System.out.println("1. Create Task");
        System.out.println("2. List All Tasks");
        System.out.println("3. Find Task by Id");
        System.out.println("4. Update Task");
        System.out.println("5. Update Task Status");
        System.out.println("6. Delete Task");
        System.out.println("0. Exit");
    }

}
