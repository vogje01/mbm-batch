package com.momentum.batch.server.database.install;

import com.momentum.batch.server.database.install.task.BatchDatabaseTasks;
import com.momentum.batch.server.database.install.types.DatabaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@SpringBootApplication(scanBasePackages = {"com.momentum.batch.common.util", "com.momentum.batch.server.database"})
public class BatchDatabaseInstallation implements CommandLineRunner {

    @Autowired
    private BatchDatabaseTasks batchDatabaseTasks;

    private static final Logger logger = LoggerFactory.getLogger(BatchDatabaseInstallation.class);

    public static void main(String[] args) {
        SpringApplication.run(BatchDatabaseInstallation.class, args);
    }

    @Override
    public void run(String... args) {
        DatabaseType databaseType = DatabaseType.MYSQL;
        String user = "root";
        String password = null;
        String databaseUrl = null;

        // Command
        String command = args[0];

        // Options
        for (int i = 1; i < args.length; ++i) {
            logger.info(format("args[{0}]: {1}", i, args[i]));
            switch (args[i]) {
                case "-t":
                    if (args[++i].equalsIgnoreCase("oracle")) {
                        databaseType = DatabaseType.ORACLE;
                    }
                    break;
                case "-u":
                    user = args[++i];
                    break;
                case "-p":
                    password = args[++i];
                    break;
                case "-d":
                    databaseUrl = args[++i];
                    break;
                case "-h":
                    usage();
                    break;
            }
        }

        switch (command) {
            case "help" -> usage();
            case "drop" -> BatchDatabaseTasks.dropDatabase(databaseUrl, user, password);
            case "create" -> BatchDatabaseTasks.createDatabase(databaseUrl, user, password);
            case "install" -> BatchDatabaseTasks.installDatabase(databaseUrl, user, password);
            case "update" -> BatchDatabaseTasks.updateDatabase(databaseUrl, user, password);
            case "encrypt" -> batchDatabaseTasks.encryptPassword(password);
        }
    }

    private void usage() {
        System.out.println(format("MBM database installation\nUsage:"));
        System.out.println(format("\tBatchDatabaseInstallation command [options]"));
        System.out.println(format("\tCommands:"));
        System.out.println(format("\t\thelp: shows the usage screen"));
        System.out.println(format("\t\tdrop: drop the current database"));
        System.out.println(format("\t\tcreate: create batch database"));
        System.out.println(format("\t\tinstall: install batch database schema"));
        System.out.println(format("\t\tupdate: update batch database schema"));
        System.out.println(format("\t\tencrypt: encrypts a password"));
        System.out.println(format("\tOptions:"));
        System.out.println(format("\t\t-t <type>: database type (supported typed: mysql | oracle)"));
        System.out.println(format("\t\t-d <url>: database url"));
        System.out.println(format("\t\t-u user: database user"));
        System.out.println(format("\t\t-p password: database password"));
        System.exit(0);
    }
}
