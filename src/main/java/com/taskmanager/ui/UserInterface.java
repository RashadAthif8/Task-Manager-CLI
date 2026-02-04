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

        // Implementar a lógica da interface do usuário aqui
        boolean running = true;

        while (running) {
            menuTaskManager();
            int option = readOption();

            switch (option) {
                case 1:
                    createTaskFlow();
                    break;
                case 2:
                    // Não precisa de um método específico na UI para listar tarefas
                    taskService.getAllTasks();
                    break;
                case 3:
                    // Não precisa de um método específico na UI para buscar tarefa por Id
                    System.out.print("Task Id to find a task: ");
                    int idFind = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    taskService.findById(idFind);
                    break;
                case 4:
                    System.out.print("Task Id to update: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    updateTaskFlow(idUpdate);
                    break;
                case 5:
                    System.out.print("Task Id to update status: ");
                    int idStatus = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    updateStatusFlow(idStatus);
                    break;
                case 6:
                    // Não precisa de um método específico na UI para deletar tarefa
                    System.out.print("Task Id to delete a task: ");
                    int idDelete = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    taskService.deleteTask(idDelete);
                    break;
                case 0:
                    running = false;
                    System.out.println();
                    System.out.println("Exiting Task Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;

            }
            
        }

    scanner.close();

    }

    // DONE: IMPLEMENTAR O MÉTODO readOption() AQUI
    // Este método deve ler a opção do usuário e retornar um inteiro
    private int readOption() {
        try {
            System.out.print("\nSelect an option: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // DONE: IMPLEMENTAR O MÉTODO createTaskFlow() AQUI
    // Este método deve guiar o usuário pelo processo de criação de uma nova tarefa
    private void createTaskFlow() {
        System.out.println("\n--- New Task ---");

        // 1. Lendo Texto Simples (Título)
        System.out.print("Title: ");
        String title = scanner.nextLine();

        // 2. Lendo Texto Longo (Descrição)
        System.out.print("Description: ");
        String description = scanner.nextLine();

        // 3. Lendo Data (Data de Vencimento)
        System.out.print("Due Date (YYYY-MM-DD): ");
        String dueDateInput = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateInput);

        taskService.createTask(title, description, dueDate);
        System.out.println("Task created successfully!\n");
        
    }

    // TO DO: IMPLEMENTAR O MÉTODO updateTaskFlow() AQUI
    // Este método deve guiar o usuário pelo processo de atualização de uma nova tarefa
    private void updateTaskFlow(int taskId) {
        System.out.println("\n--- Update Task ---");
        // 1. Verificar se a tarefa existe antes de perguntar qualquer coisa
        Task task = taskService.findById(taskId);

        if (task == null) {
            System.out.println("Task with Id " + taskId + " not found.");
            return;
        }  

        // 2. Mostrar os dados atuais da tarefa
        System.out.println("\n--- Updating Task " + taskId + " ---");
        System.out.println("Current Data: " + task.getTitle() + "\n" + task.getDescription() + "\n" + task.getDueDate());

        // 2. Coletamos os novos dados
        System.out.print("New Title: ");
        String newTitle = scanner.nextLine();

        System.out.print("New Descriptin: ");
        String newDescription = scanner.nextLine();

        System.out.print("New Due Date (YYYY-MM-DD): ");
        LocalDate newDueDate = LocalDate.parse(scanner.nextLine());

        // 3. Criamos um objeto Task temporário com os novos dados
        // IMPORTANTE: O ID deve ser o mesmo da tarefa que queremos mudar
        Task taskAtualizada = new Task(newTitle, newDescription, newDueDate);
        taskAtualizada.setId((long) taskId); // O ID que veio do parâmetro
        taskAtualizada.setStatus(task.getStatus()); // Mantém o status atual

        // 4. Enviamos para o Service fazer a substituição
        taskService.updateTask(taskAtualizada);
    }

   // TO DO: IMPLEMENTAR MÉTODO DE ATUALIZAÇÃO DE STATUS DA TAREFA AQUI
   // Este método deve guiar o usuário pelo processo de atualização do status de uma tarefa
   private void updateStatusFlow(int taskId) {
        System.out.println("\n--- Update Task Status ---");

        // 1. Verificar se a tarefa existe antes de perguntar qualquer coisa
        Task task = taskService.findById(taskId);
        if (task == null) {
            System.out.println("Task with Id " + taskId + " not found.");
            return;
        } 

        // 2. Mostrar o status atual da tarefa
        System.out.println("Current Status: " + task.getStatus());

        // 3. Coletar o novo status
        System.out.print("New Status (DONE, IN_PROGRESS, TO DO, CANCELLED): ");
        String newStatusInput = scanner.nextLine();
        
        // 4. Atualizar o status da tarefa com base na entrada do usuário
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

    // DONE: IMPLEMENTAR O MÉTODO menuTaskManager() AQUI
    // Este método deve exibir o menu principal do gerenciador de tarefas
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
