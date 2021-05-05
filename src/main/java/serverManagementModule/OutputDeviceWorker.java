package serverManagementModule;

import server_messages.ServerMessage;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Class for working with the input device
 */
public class OutputDeviceWorker {
    private static OutputDeviceWorker describer;
    private OutputStream outputStream;
    private ServerMessage serverMessage;
    private OutputDeviceWorker() {
        this.serverMessage = new ServerMessage("");
    }

    public void setOutputStream(OutputStream out) {
        this.outputStream = out;
    }
    /**
     * Static Method to init Output Device for the first time and then get this Output Device.
     *
     * @return Output Device
     */
    public static OutputDeviceWorker getOutputDevice() {
        if (OutputDeviceWorker.describer == null) OutputDeviceWorker.describer = new OutputDeviceWorker();
        return OutputDeviceWorker.describer;
    }


    /**
     * Method for describe file which not specified
     */
    public void describeFileNotSpecified() {
        System.out.println("the environment variable is not set, the collection will not be loaded and will not be saved");
    }

    public void describeString(String s) {
        System.out.println(s);
    }
    /**
     * Method for send message to client
     * @param message to sent to client
     */
    public void createMessage(String message) {
        String currentMessage = serverMessage.getMessage() +
                message;
        this.serverMessage = new ServerMessage(currentMessage);
    }

    /**
     * Method for send have already built serverMessage to client
     */
    public void setExecutingScriptFlag() {
        this.serverMessage.setEndOfScriptExFlag();
    }

    public void sendMessage() {
        try {
            ByteBuffer buffer;
            try(ByteArrayOutputStream byteArrayOStream = new ByteArrayOutputStream()) {
                try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOStream)) {
                    out.writeObject(serverMessage);
                    buffer = ByteBuffer.wrap(byteArrayOStream.toByteArray());
                }
            }
            outputStream.write(buffer.array());
            this.serverMessage = new ServerMessage("");
        } catch (IOException e) {
            OutputDeviceWorker.getOutputDevice().describeString("Error sending the message");
        }
    }
    /**
     * Method for describe Exception
     * @param e to describe exception
     */
    public void describeException(Exception e) {
        System.out.println(e.getMessage());
    }

}
