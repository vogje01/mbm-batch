package com.momentum.batch.server.database.install.task;

import org.flywaydb.core.Flyway;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchDatabaseTasksMysql {

    private final StringEncryptor stringEncryptor;

    @Autowired
    public BatchDatabaseTasksMysql(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

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

            String sql = "DROP DATABASE IF EXISTS batch";
            stmt.executeUpdate(sql);
            sql = "DROP USER 'admin'@'%'";
            stmt.executeUpdate(sql);

        } catch (Exception se) {
            // Handle errors for JDBC
            se.printStackTrace();
            System.err.println(format("Error: {0}", se.getMessage()));
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
            sql = "CREATE USER 'admin'@'%' IDENTIFIED BY 'Secret_123'";
            stmt.executeUpdate(sql);
            sql = "GRANT ALL PRIVILEGES ON batch.* TO 'admin'@'%'";
            stmt.executeUpdate(sql);
            sql = "FLUSH PRIVILEGES";
            stmt.executeUpdate(sql);
            System.out.println("Batch database created");

        } catch (Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void installDatabase(String url, String user, String password) {

        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(url, user, password)
                .locations("classpath:db.migration.mysql")
                .load();

        // Start the migration
        flyway.migrate();

        System.out.println(format("Installing database - version: {0}", flyway.info().current().getVersion()));
    }

    public static void updateDatabase(String url, String user, String password) {

        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(url, user, password)
                .locations("classpath:db.migration.mysql")
                .load();

        // Start the migration
        flyway.migrate();

        System.out.println(format("Database updated - version: {0}", flyway.info().current().getVersion()));
    }

    public void encryptPassword(String userPassword) {
        System.out.println("Encrypted password: " + stringEncryptor.encrypt(userPassword));
    }
}
