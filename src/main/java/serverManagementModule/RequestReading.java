package serverManagementModule;


import commands.ReceivedCommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RequestReading {
    private final Socket client;

    public RequestReading(Socket client) {
        this.client = client;
    }

    public ReceivedCommand readCommand() throws IOException{
        ReceivedCommand receivedCommand = null;
        SocketChannel clientSocketChannel = client.getChannel();
        byte[] bytes = new byte[2048];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        clientSocketChannel.read(buffer);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
             ObjectInputStream in = new ObjectInputStream(bis)) {
            receivedCommand = (ReceivedCommand)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            OutputDeviceWorker.getOutputDevice().describeException(e);
        }
        return receivedCommand;
    }
}
