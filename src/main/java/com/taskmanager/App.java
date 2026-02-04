package com.taskmanager;

import com.taskmanager.config.DatabaseConfig;
import com.taskmanager.ui.UserInterface;

public class App 
{
    public static void main( String[] args )
    {
        // Inicializa o banco de dados (cria a tabela se não existir)
        DatabaseConfig.initializeDatabase();
        
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
