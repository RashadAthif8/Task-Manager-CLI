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

    // Construtor SEM o parâmetro id
    public Task(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = StatusEnum.TO_DO;
    }

    // Método para setar o ID (será chamado pelo TaskManager)
    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public void showStatus(){
        //System.out.println("------ Status da tarefa: " + this.status + " ------");
        System.out.println("ID: " + this.id);
        System.out.println("Titulo: " + this.title);
        System.out.println("Descricao: " + this.description);
        System.out.println("Data Final: " + this.dueDate);
        System.out.println("Status: " + this.status);

        if (isOverdue()) {
            System.out.println("----------");
            System.out.println("Atencao: Esta tarefa esta atrasada!");
            System.out.println("----------");
        }

        if (isDueDate()) {
            System.out.println("----------");
            System.out.println("Lembrete: Esta tarefa deve ser realizada ainda hoje!");
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
