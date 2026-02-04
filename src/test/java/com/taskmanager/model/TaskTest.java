package com.taskmanager.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import com.taskmanager.model.enums.StatusEnum;

/**
 * Testes unitários para a classe Task
 */
public class TaskTest {
    
    private Task task;
    
    /**
     * Executa ANTES de cada teste
     * Cria uma tarefa padrão para usar nos testes
     */
    @BeforeEach
    public void setUp() {
        task = new Task(
            "Estudar Java",
            "Revisar conceitos de POO",
            LocalDate.of(2026, 1, 15)
        );
    }
    
    // ========== TESTES DO CONSTRUTOR ==========
    
    @Test
    @DisplayName("Deve criar uma Task com os valores corretos")
    public void testTaskCreation() {
        assertNotNull(task, "Task não deve ser null");
        assertEquals("Estudar Java", task.getTitle(), "Título deve ser 'Estudar Java'");
        assertEquals("Revisar conceitos de POO", task.getDescription(), "Descrição incorreta");
        assertEquals(LocalDate.of(2026, 1, 15), task.getDueDate(), "Data de vencimento incorreta");
        assertEquals(StatusEnum.TO_DO, task.getStatus(), "Status inicial deve ser TO_DO");
        assertNull(task.getId(), "ID inicial deve ser null");
    }
    
    @Test
    @DisplayName("Deve sempre criar Task com status TO_DO independente do parâmetro")
    public void testTaskAlwaysStartsWithToDo() {
        Task task2 = new Task("Título", "Desc", LocalDate.now());
        assertEquals(StatusEnum.TO_DO, task2.getStatus(), "Status deve sempre iniciar como TO_DO");
    }
    
    // ========== TESTES DE GETTERS E SETTERS ==========
    
    @Test
    @DisplayName("Deve modificar o título corretamente")
    public void testSetTitle() {
        task.setTitle("Novo Título");
        assertEquals("Novo Título", task.getTitle(), "Título não foi atualizado");
    }
    
    @Test
    @DisplayName("Deve modificar a descrição corretamente")
    public void testSetDescription() {
        task.setDescription("Nova descrição");
        assertEquals("Nova descrição", task.getDescription(), "Descrição não foi atualizada");
    }
    
    @Test
    @DisplayName("Deve modificar a data de vencimento corretamente")
    public void testSetDueDate() {
        LocalDate newDate = LocalDate.of(2026, 12, 31);
        task.setDueDate(newDate);
        assertEquals(newDate, task.getDueDate(), "Data não foi atualizada");
    }
    
    @Test
    @DisplayName("Deve modificar o status corretamente")
    public void testSetStatus() {
        task.setStatus(StatusEnum.WIP);
        assertEquals(StatusEnum.WIP, task.getStatus(), "Status não foi atualizado");
    }
    
    // ========== TESTES DO MÉTODO setId() ==========
    
    @Test
    @DisplayName("Deve setar o ID quando for null")
    public void testSetIdWhenNull() {
        task.setId(1L);
        assertEquals(1L, task.getId(), "ID deve ser 1");
    }
    
    @Test
    @DisplayName("Não deve modificar o ID se já existir")
    public void testSetIdWhenNotNull() {
        task.setId(1L);
        task.setId(2L); // Tenta mudar
        assertEquals(1L, task.getId(), "ID não deve ser modificado após ser setado");
    }
    
    // ========== TESTES DO MÉTODO markAsCompleted() ==========
    
    @Test
    @DisplayName("Deve marcar a tarefa como DONE")
    public void testMarkAsCompleted() {
        task.markAsCompleted();
        assertEquals(StatusEnum.DONE, task.getStatus(), "Status deve ser DONE após marcar como completo");
    }
    
    @Test
    @DisplayName("Deve manter DONE mesmo se chamar novamente")
    public void testMarkAsCompletedTwice() {
        task.markAsCompleted();
        task.markAsCompleted();
        assertEquals(StatusEnum.DONE, task.getStatus(), "Status deve continuar DONE");
    }
    
    // ========== TESTES DO MÉTODO isOverdue() ==========
    
    @Test
    @DisplayName("Deve retornar false quando a data não passou")
    public void testIsNotOverdue() {
        Task futureTask = new Task(
            "Tarefa Futura",
            "Descrição",
            LocalDate.now().plusDays(5)
        );
        assertFalse(futureTask.isOverdue(), "Tarefa futura não deve estar atrasada");
    }
    
    @Test
    @DisplayName("Deve retornar true quando a data passou")
    public void testIsOverdue() {
        Task pastTask = new Task(
            "Tarefa Passada",
            "Descrição",
            LocalDate.now().minusDays(5)
        );
        assertTrue(pastTask.isOverdue(), "Tarefa com data passada deve estar atrasada");
    }
    
    @Test
    @DisplayName("Nao deve considerar atrasada se status for DONE")
    public void testIsOverdueWhenDone() {
        Task doneTask = new Task(
            "Tarefa Concluída",
            "Descrição",
            LocalDate.now().minusDays(5)
        );
        doneTask.markAsCompleted();
        assertFalse(doneTask.isOverdue(), "Tarefa DONE nao deve ser considerada atrasada");
    }
    
    @Test
    @DisplayName("Nao deve considerar atrasada se a data eh hoje")
    public void testIsOverdueToday() {
        Task todayTask = new Task(
            "Tarefa de Hoje",
            "Descrição",
            LocalDate.now()
        );
        assertFalse(todayTask.isOverdue(), "Tarefa de hoje nao deve estar atrasada");
    }
    
    // ========== TESTES DO MÉTODO isDueDate() ==========
    
    @Test
    @DisplayName("Deve retornar true quando a data eh hoje")
    public void testIsDueDateToday() {
        Task todayTask = new Task(
            "Tarefa de Hoje",
            "Descrição",
            LocalDate.now()
        );
        assertTrue(todayTask.isDueDate(), "Deve retornar true para data de hoje");
    }
    
    @Test
    @DisplayName("Deve retornar false quando a data nao eh hoje")
    public void testIsDueDateNotToday() {
        Task futureTask = new Task(
            "Tarefa Futura",
            "Descrição",
            LocalDate.now().plusDays(1)
        );
        assertFalse(futureTask.isDueDate(), "Deve retornar false para data futura");
        
        Task pastTask = new Task(
            "Tarefa Passada",
            "Descrição",
            LocalDate.now().minusDays(1)
        );
        assertFalse(pastTask.isDueDate(), "Deve retornar false para data passada");
    }
    
    // ========== TESTES DE CENÁRIOS COMPLEXOS ==========
    
    @Test
    @DisplayName("Deve funcionar corretamente em um fluxo completo")
    public void testCompleteWorkflow() {
        // 1. Criar tarefa
        Task workflowTask = new Task(
            "Implementar Feature",
            "Nova funcionalidade do sistema",
            LocalDate.now().plusDays(7)
        );
        
        // 2. Setar ID
        workflowTask.setId(100L);
        assertEquals(100L, workflowTask.getId());
        
        // 3. Mudar status para WIP
        workflowTask.setStatus(StatusEnum.WIP);
        assertEquals(StatusEnum.WIP, workflowTask.getStatus());
        
        // 4. Verificar que não está atrasada
        assertFalse(workflowTask.isOverdue());
        
        // 5. Completar tarefa
        workflowTask.markAsCompleted();
        assertEquals(StatusEnum.DONE, workflowTask.getStatus());
        
        // 6. Mesmo se a data passar, não deve estar atrasada (está DONE)
        workflowTask.setDueDate(LocalDate.now().minusDays(10));
        assertFalse(workflowTask.isOverdue());
    }
}
