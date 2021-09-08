package data_base;

import data_base.db_url.DBurl;
import server_management_module.OutputDeviceWorker;

import java.sql.*;

public class DatabaseCommunicator {
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
            connection = DriverManager.getConnection(DBurl.getDbUrl(), DBurl.getUSER(), DBurl.getPASS());
            statement = connection.createStatement();
            statement.execute("create table if not exists routes\n" +
                    "(\n" +
                    "    routeid            serial not null\n" +
                    "        constraint routes_pkey\n" +
                    "            primary key,\n" +
                    "    routename          varchar(20),\n" +
                    "    creationdate       timestamp,\n" +
                    "    routecoordinatex   integer,\n" +
                    "    routecoordinatey   real,\n" +
                    "    locationfromcoordx integer,\n" +
                    "    locationfromcoordy integer,\n" +
                    "    locationfromcoordz integer,\n" +
                    "    locationtocoordx   integer,\n" +
                    "    locationtocoordy   double precision,\n" +
                    "    locationtocoordz   integer,\n" +
                    "    locationtoname     varchar(20),\n" +
                    "    distance           double precision\n," +
                    "    routecreator       varchar(20)\n" +
                    ")");
            statement.execute("create table if not exists login\n" +
                    "(\n" +
                    "    username     varchar(20) not null\n" +
                    "    constraint login_pkey\n" +
                    "    primary key,\n" +
                    "    userpassword varchar(100)\n" +
                    "    )");
            statement.execute("create sequence if not exists routes_routeid_seq");
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

    public void setWorkingDB(boolean workingDB) {
        isWorkingDB = workingDB;
    }
}
