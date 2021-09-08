import collection_management_module.LoginCollectionManagement;
import collection_management_module.RouteCollectionManagement;
import data_base.AdapterToCollection.AdapterToLoginCollection;
import data_base.AdapterToCollection.AdapterToRouteCollection;
import data_base.DBWorker;
import data_base.DatabaseCommunicator;
import server_management_module.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.Random;


/**
 * Main class that runs the server
 */
public class Server {
    /**
     * Static method that started when server run
     * @param args to set args
     */
    static public void main(String[] args) {
       ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Server can't be opened");
            return;
        }
        int port = choosePort(serverSocketChannel, 1221);
        OutputDeviceWorker.getOutputDevice().describeString("The server is running on the address: " + serverSocketChannel.socket().getInetAddress() + ":" + port);
        DatabaseCommunicator DBc = DatabaseCommunicator.getDatabaseCommunicator();
        DBc.connect();
        LoginCollectionManagement loginCollectionManagement = new LoginCollectionManagement();
        RouteCollectionManagement routeCollectionManagement = new RouteCollectionManagement(new DBWorker(loginCollectionManagement));
        AdapterToLoginCollection adapterToLoginCollection = new AdapterToLoginCollection(
                DatabaseCommunicator.getDatabaseCommunicator().getStatement(), loginCollectionManagement);
        AdapterToRouteCollection adapterToRouteCollection = new AdapterToRouteCollection(
                DatabaseCommunicator.getDatabaseCommunicator().getStatement(), routeCollectionManagement);
        if (DBc.isWorkingDB()) {
            try {
                adapterToLoginCollection.adapt();
                adapterToRouteCollection.adapt();
            } catch (SQLException e) {
                OutputDeviceWorker.getOutputDevice().describeString("adapting the database to the collection does not work, check that the database is correct");
                DatabaseCommunicator.getDatabaseCommunicator().setWorkingDB(false);
            }
        }
        ServerWorker serverWorker= new ServerWorker(serverSocketChannel, routeCollectionManagement, loginCollectionManagement);
        serverWorker.startWithThread();
    }

    private static int choosePort(ServerSocketChannel serverSocketChannel, int port) {
        SocketAddress socketAddr = new InetSocketAddress("localhost", port);
        try {
            serverSocketChannel.bind(socketAddr);
        } catch (IOException e) {
            choosePort(serverSocketChannel, new Random().nextInt(65536));
        }
        return port;
    }

}
