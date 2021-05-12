package serverManagementModule;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Class for set and accept connection of user
 */
public class AcceptingConnections {
    private final ServerSocketChannel server;

    /**
     * Constructor for set server socket
     * @param server to set server socket
     */
    public AcceptingConnections(ServerSocketChannel server) {
        this.server = server;
    }

    /**
     * Method for accepting connections
     * @return socket(client socket)
     */
    public SocketChannel connectAccept() {
        SocketChannel client = null;
        try {
            client = server.accept();
            OutputDeviceWorker.getOutputDevice().describeString("Connection established with " + client.socket().getRemoteSocketAddress());
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Unable to connect with client");
        }
        return client;
    }

}
