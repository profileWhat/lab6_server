package serverManagementModule;

import commands.ClientCommandName;
import commands.Command;
import commands.CommandName;

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
        for (ClientCommandName clientCommandName : ClientCommandName.values()) {
            if (clientCommandName.name().equals("UNDEFINED")) continue;
            String message;
            Command command = server.getCommandInvoker().getCommandMap().get(CommandName.valueOf(clientCommandName.name()));
            message = clientCommandName.toString().toLowerCase() + ": ";
            OutputDeviceWorker.getOutputDevice().createMessage(message);
            command.describe();
            OutputDeviceWorker.getOutputDevice().createMessage("\n");
        }
    }

    /**
     * Method for execute ExecuteScript Command.
     * @param fileName to start the script execution with the specified name
     */
    public void executeScript(String fileName) {
        OutputDeviceWorker.getOutputDevice().createMessage("the script execution has started \n");
        int fileNameSetSize = fileNameSet.size();
        this.fileNameSet.add(fileName);
        if (fileNameSet.size() == fileNameSetSize) {
            String message = "Try to reuse script, executing script aborted";
            OutputDeviceWorker.getOutputDevice().createMessage(message);
            OutputDeviceWorker.getOutputDevice().setExecutingScriptFlag();
        }
    }

    /**
     * Method for execute Exit Command.
     */
    public void exit() {
        OutputDeviceWorker.getOutputDevice().createMessage("The program finished \n");
    }


    /**
     * Method for describe not define commands
     */
    public void notDefine() {
        OutputDeviceWorker.getOutputDevice().createMessage("Undefined command can't be executed \n");
    }
}
