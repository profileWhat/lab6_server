import collectionManagementModule.CollectionManagement;
import fileManagementModule.FileWorker;
import fileManagementModule.JsonWorker;
import serverManagementModule.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
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
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Server can't be opened");
        }
        int port = choosePort(serverSocketChannel);
        OutputDeviceWorker.getOutputDevice().describeString("The server is running on the port: " + port);
        String filePath = System.getenv("INPUT_FILE_PATH");
        FileWorker.getFileWorker().setFileName(filePath);
        CollectionManagement collectionManagement = new CollectionManagement();
        collectionManagement.addRoutes(JsonWorker.getJsonWorker().deserializeToRouteArray());
        ServerWorker serverWorker= new ServerWorker(serverSocketChannel.socket(), collectionManagement);
        serverWorker.start();
    }

    private static int choosePort(ServerSocketChannel serverSocketChannel) {
        int port = new Random().nextInt(65535);
        SocketAddress socketAddr = new InetSocketAddress("localhost", port);
        try {
            serverSocketChannel.bind(socketAddr);
        } catch (IOException e) {
            choosePort(serverSocketChannel);
        }
        return port;
    }

}
