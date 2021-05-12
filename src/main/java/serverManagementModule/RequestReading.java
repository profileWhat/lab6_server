package serverManagementModule;


import commands.ClientCommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Class for reading request from client
 */
public class RequestReading {
    private final SocketChannel clientChannel;
    /**
     * Constructor for set socket client
     * @param clientChannel to set it
     */
    public RequestReading(SocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    /**
     * Method for read command that client sent to server
     * @return received command
     * @throws IOException if connection has been broken
     */
    public ClientCommand readCommand() throws IOException{
        ClientCommand clientCommand = null;
        byte[] bytes = new byte[1024];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        clientChannel.read(buffer);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
             ObjectInputStream in = new ObjectInputStream(bis)) {
            clientCommand = (ClientCommand)in.readObject();
        } catch (ClassNotFoundException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Unable to read the command");
        }
        return clientCommand;
    }

}
