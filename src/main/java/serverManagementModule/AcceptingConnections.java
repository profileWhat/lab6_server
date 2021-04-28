package serverManagementModule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class for set and accept connection of user
 */
public class AcceptingConnections {
    private final ServerSocket server;

    /**
     * Constructor for set server socket
     * @param server to set server socket
     */
    public AcceptingConnections(ServerSocket server) {
        this.server = server;
    }

    /**
     * Method for accepting connections
     * @return socket(client socket)
     */
    public Socket connectAccept() {
        Socket client = null;
        try {
            client = server.accept();
            OutputDeviceWorker.getOutputDevice().setOutputStream(client.getOutputStream());
            OutputDeviceWorker.getOutputDevice().describeString("Connection established with " + client.getInetAddress());
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeException(e);
        }
        return client;
    }

}
