package com.taskmanager.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.taskmanager.config.DatabaseConfig;
import com.taskmanager.model.Task;
import com.taskmanager.model.enums.StatusEnum;

public class TaskRepository {
    // Implementação do método para salvar tarefas
    public void saveTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, due_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, task.getTitle());
                pstmt.setString(2, task.getDescription());
                pstmt.setString(3, task.getDueDate().toString());
                pstmt.setString(4, task.getStatus().name());
                
                pstmt.executeUpdate();

             }
        catch (Exception e) {
            System.out.println("Erro ao salvar a tarefa: " + e.getMessage());
        }

    }

    // Método para buscar todas as tarefas
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseConfig.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                    
            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            } 
        } catch (Exception e) {
            System.out.println("Erro ao buscar tarefas: " + e.getMessage());
        }
        return tasks;
    }

    // --- BUSCAR POR ID ---
    public Task findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTask(rs);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar tarefa: " + e.getMessage());
        }
        return null;
    }

    // --- ATUALIZAR ---
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getDueDate().toString());
            pstmt.setString(4, task.getStatus().name());
            pstmt.setLong(5, task.getId());
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    // --- DELETAR ---
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se algo foi deletado
            
        } catch (Exception e) {
            System.err.println("Erro ao deletar tarefa: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para converter a linha do banco em Objeto Java
    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate dueDate = LocalDate.parse(rs.getString("due_date"));

        Task task = new Task(title, description, dueDate);
        task.setId(rs.getLong("id"));
        task.setStatus(StatusEnum.valueOf(rs.getString("status")));
        return task;
    }
}
