package serverManagementModule;

import commands.Command;
import commands.CommandName;
import commands.ReceivedCommandName;

import java.util.HashSet;


/**
 * Receiver class
 * <p>
 * receiver class that works with the client and with the I/O device
 */

public class ClientCommandReceiver {
    private final ServerWorker server;
    private final HashSet<String> fileNameSet;
    /**
     * Constructor of ClientCommandReceiver.
     *
     * @param client for works with Client
     */
    public ClientCommandReceiver(ServerWorker client) {
        this.server = client;
        this.fileNameSet = new HashSet<>();
    }

    /**
     * Method for execute Help command.
     */
    public void help() {
        for (ReceivedCommandName receivedCommandName: ReceivedCommandName.values()) {
            Command command = server.getCommandInvoker().getCommandMap().get(CommandName.valueOf(receivedCommandName.name()));
            OutputDeviceWorker.getOutputDevice().sendMessage(receivedCommandName.toString().toLowerCase() + ": ");
            command.describe();
            OutputDeviceWorker.getOutputDevice().sendMessage("\n");
        }
    }

    /**
     * Method for execute ExecuteScript Command.
     * @param fileName to start the script execution with the specified name
     */
    public void executeScript(String fileName) {
        OutputDeviceWorker.getOutputDevice().sendMessage("the script execution has started \n");
        int fileNameSetSize = fileNameSet.size();
        this.fileNameSet.add(fileName);
        if (fileNameSet.size() == fileNameSetSize) OutputDeviceWorker.getOutputDevice().sendEndOfScriptExFlag();
    }

    /**
     * Method for execute Exit Command.
     */
    public void exit() {
        OutputDeviceWorker.getOutputDevice().sendMessage("The program finished \n");
    }


    /**
     * Method for describe not define commands
     */
    public void notDefine() {
        OutputDeviceWorker.getOutputDevice().sendMessage("Undefined command can't be executed \n");
    }
}
