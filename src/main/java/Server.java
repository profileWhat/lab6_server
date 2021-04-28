import collectionManagementModule.CollectionManagement;
import fileManagementModule.FileWorker;
import fileManagementModule.JsonWorker;
import serverManagementModule.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * Main class that runs the server
 */
public class Server {
    private static final SocketAddress socketAddr = new InetSocketAddress("localhost", 1221);

    /**
     * Static method that started when server run
     * @param args to set args
     */
    static public void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(socketAddr);
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeException(e);
        }
        OutputDeviceWorker.getOutputDevice().describeString("The server is running");
        String filePath = System.getenv("INPUT_FILE_PATH");
        FileWorker.getFileWorker().setFileName(filePath);
        CollectionManagement collectionManagement = new CollectionManagement();
        collectionManagement.addRoutes(JsonWorker.getJsonWorker().deserializeToRouteArray());
        ServerWorker serverWorker= new ServerWorker(serverSocketChannel.socket(), collectionManagement);
        serverWorker.start();
    }

}
