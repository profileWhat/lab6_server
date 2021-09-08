package server_management_module.server_request_reading;

import client_messages.ClientMessage;
import server_management_module.OutputDeviceWorker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadingMessage<T extends ClientMessage> {
    private T clientMessage;

    public void read(SocketChannel clientChannel) throws IOException {
        byte[] bytes = new byte[32757];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        clientChannel.read(buffer);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
             ObjectInputStream in = new ObjectInputStream(bis)) {
            clientMessage = (T)in.readObject();
        } catch (ClassNotFoundException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Unable to read the command");
        }

    }

    public ClientMessage getContent() {
        return clientMessage;
    }
}
