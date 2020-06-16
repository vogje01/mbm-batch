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
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class BatchDatabaseTasks {

    private final StringEncryptor stringEncryptor;

    @Autowired
    public BatchDatabaseTasks(StringEncryptor stringEncryptor) {
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

    /**
     * Creates the admin user.
     *
     * <p>
     * In ase it fails:
     * <pre>
     *          UPDATE mysql.user SET Grant_priv='Y', Super_priv='Y' WHERE User='root';
     *          FLUSH PRIVILEGES;
     *          GRANT ALL ON *.* TO 'root'&amp;'localhost';
     *     </pre>
     * and re-login.
     * </p>
     *
     * @param url      database URL.
     * @param user     root user.
     * @param password root password.
     */
    public static void createAdminUser(String url, String user, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(url, user, password);

            // Execute a query
            stmt = conn.createStatement();

            String sql = "CREATE USER 'admin'@'%' IDENTIFIED BY 'Secret_123'";
            stmt.executeUpdate(sql);
            sql = "GRANT ALL PRIVILEGES ON batch.* TO 'admin'@'%'";
            stmt.executeUpdate(sql);
            sql = "FLUSH PRIVILEGES";
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
        System.out.println("Batch admin user created");
    }

    public static void installDatabase(String url, String user, String password) {

        System.out.println(format("Installing database - user: {0} password: {1} url: {2}", user, password, url));

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

    public void encryptPassword(String userPassword) {
        System.out.println("Encrypted password: " + stringEncryptor.encrypt(userPassword));
    }
}
