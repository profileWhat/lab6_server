package server_management_module;


import collection_management_module.RouteCollectionManagement;
import commands.ClientCommand;
import commands.CommandName;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Class for processing input commands
 */
public class CommandHandler {
    private final CommandInvoker commandInvoker;
    private final RouteCollectionManagement routeCollectionManagement;
    /**
     * Constructor of Command Handler. Load Command Invoker to execute Command, set flag of is Found Exit Command, is Executing Script.
     *
     * @param commandInvoker to execute Command
     */
    public CommandHandler(CommandInvoker commandInvoker, RouteCollectionManagement routeCollectionManagement) {
        this.routeCollectionManagement = routeCollectionManagement;
        this.commandInvoker = commandInvoker;
    }

    /**
     * Method for execute command by command type
     * @param clientCommand to get received command type
     */
    public void execute(ClientCommand clientCommand, String userName) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        routeCollectionManagement.setUserName(userName);
        CommandName commandName = CommandName.valueOf(clientCommand.getCommandName().toString());
        commandInvoker.execute(commandName, clientCommand.getArgument());
        lock.unlock();
    }

}
