package data_base;

import server_management_module.OutputDeviceWorker;

import java.sql.*;

public class DatabaseCommunicator {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/routes";
    private static final String USER = "postgres";
    private static final String PASS = "7ppgd877";
    private boolean isWorkingDB = false;
    private Connection connection;
    private Statement statement;
    private static DatabaseCommunicator databaseCommunicator;
    private DatabaseCommunicator() {
        this.connection = null;
    }

    public static DatabaseCommunicator getDatabaseCommunicator() {
        if (databaseCommunicator == null) databaseCommunicator = new DatabaseCommunicator();
        return databaseCommunicator;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            isWorkingDB = true;
        } catch (SQLException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Database connection can't be establish");
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public Statement getStatement() {
        return statement;
    }

    public boolean isWorkingDB() {
        return isWorkingDB;
    }
}
