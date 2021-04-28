package serverManagementModule;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptingConnections {
    private ServerSocket server;

    public AcceptingConnections(ServerSocket server) {
        this.server = server;
    }

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
