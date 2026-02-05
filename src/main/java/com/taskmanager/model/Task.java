package com.taskmanager.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.taskmanager.model.enums.StatusEnum;

public class Task {
    @Getter
    private Long id;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private LocalDate dueDate;

    @Getter @Setter
    private StatusEnum status;

    // Constructor WITHOUT the id parameter
    public Task(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = StatusEnum.TO_DO;
    }

    // Method to set the ID (will be called by TaskManager)
    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public void showStatus(){
        System.out.println("------ Task status: " + this.status + " ------");
        System.out.println("ID: " + this.id);
        System.out.println("Title: " + this.title);
        System.out.println("Description: " + this.description);
        System.out.println("Due Date: " + this.dueDate);
        System.out.println("Status: " + this.status);

        if (isOverdue()) {
            System.out.println("----------");
            System.out.println("Attention: This task is overdue!");
            System.out.println("----------");
        }

        if (isDueDate()) {
            System.out.println("----------");
            System.out.println("Reminder: This task must be completed today!");
            System.out.println("----------");

        }
    }

    public void markAsCompleted() {
        this.status = StatusEnum.DONE;
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(this.dueDate) && this.status != StatusEnum.DONE;
    }

    public boolean isDueDate() {
        return LocalDate.now().isEqual(this.dueDate);
    }
}
