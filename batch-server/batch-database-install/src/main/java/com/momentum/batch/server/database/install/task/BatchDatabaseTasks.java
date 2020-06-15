package com.momentum.batch.server.database.install.task;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class BatchDatabaseTasks {

    public static void dropDatabase(String url, String user, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, user, password);

            // Execute a query
            stmt = conn.createStatement();

            String sql = "DROP DATABASE batch";
            stmt.executeUpdate(sql);

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
                // do nothing
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Batch database dropped");
    }

    public static void createDatabase(String url, String user, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, user, password);

            // Execute a query
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE batch";
            stmt.executeUpdate(sql);

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
                // do nothing
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Batch database created");
    }

    public static void installDatabase(String url, String user, String password) {

        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();

        // Start the migration
        flyway.migrate();
    }

    public static void updateDatabase(String url, String user, String password) {

        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();

        // Start the migration
        flyway.migrate();
    }
}
