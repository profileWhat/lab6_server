package server_management_module.server_request_reading;

import client_messages.ClientCommandMessage;
import client_messages.ClientStringMessage;
import commands.ClientCommand;
import server_management_module.OutputDeviceWorker;

import java.io.IOException;
import java.nio.channels.SocketChannel;


/**
 * Class for reading request from client
 */
public class ReadingRequestManager {
    private final SocketChannel clientChannel;
    /**
     * Constructor for set socket client
     * @param clientChannel to set it
     */
    public ReadingRequestManager(SocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public ClientCommand getClientCommand() throws IOException{
        ReadingMessage<ClientCommandMessage> readingMessage = new ReadingMessage<>();
        readingMessage.read(clientChannel);
        return (ClientCommand)readingMessage.getContent().getMessage();
    }

    public String getClientString() {
        ReadingMessage<ClientStringMessage> readingMessage = new ReadingMessage<>();
        try {
            readingMessage.read(clientChannel);
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeString("no connection to this client, unable to make a request");
        }
        return (String)readingMessage.getContent().getMessage();
    }

}
