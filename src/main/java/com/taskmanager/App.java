package com.taskmanager;

import com.taskmanager.config.DatabaseConfig;
import com.taskmanager.ui.UserInterface;

public class App 
{
    public static void main( String[] args )
    {
        DatabaseConfig.initializeDatabase();
        
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
