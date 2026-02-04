package com.taskmanager.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConfig {
    // Defining the data directory in the project's "data" folder
    private static final String DB_NAME = "taskmanager.db";
    private static final String DB_DIR = "data";
    private static final String DB_PATH = DB_DIR + File.separator + DB_NAME;

    public static Connection getConnection() throws Exception {
        // Ensures that the data folder exists in the project
        File dir = new File(DB_DIR);
        if(!dir.exists()) {
            dir.mkdir();
        }

        String url = "jdbc:sqlite:" + DB_PATH;
        return DriverManager.getConnection(url);
    }

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT, " +
                "due_date TEXT NOT NULL, " +
                "status TEXT NOT NULL" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
