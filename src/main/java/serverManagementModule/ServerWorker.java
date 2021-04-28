package serverManagementModule;


import collectionManagementModule.*;
import commands.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class of working with the client
 */
public class ServerWorker {
    private final CommandHandler commandHandler;
    private final CollectionManagement collectionManagement;
    private final CommandInvoker commandInvoker;
    private final int executionDepth;
    private final ServerSocket server;
    /**
     * Constructor of Client Worker. Load all param, init Client Command Receiver, Command Handler to work with input Command, Reader to read Values from file and register Command.
     * @param collectionManagement to load collection to Client Worker
     * @param executeDepth         to detect the depth execution
     */
    public ServerWorker(ServerSocket server, CollectionManagement collectionManagement, int executeDepth) {
        this.server = server;
        this.collectionManagement = collectionManagement;
        this.commandInvoker = new CommandInvoker();
        this.executionDepth = executeDepth + 1;
        ClientCommandReceiver clientReceiver = new ClientCommandReceiver(this);
        commandInvoker.register(CommandName.INFO, new InfoCommand(collectionManagement));
        commandInvoker.register(CommandName.SHOW, new ShowCommand(collectionManagement));
        commandInvoker.register(CommandName.ADD, new AddCommand(collectionManagement));
        commandInvoker.register(CommandName.UPDATE, new UpdateCommand(collectionManagement));
        commandInvoker.register(CommandName.REMOVE_BY_ID, new RemoveByIdCommand(collectionManagement));
        commandInvoker.register(CommandName.CLEAR, new ClearCommand(collectionManagement));
        commandInvoker.register(CommandName.SAVE, new SaveCommand(collectionManagement));
        commandInvoker.register(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand(clientReceiver));
        commandInvoker.register(CommandName.EXIT, new ExitCommand(clientReceiver));
        commandInvoker.register(CommandName.REMOVE_FIRST, new RemoveFirstCommand(collectionManagement));
        commandInvoker.register(CommandName.REMOVE_GREATER, new RemoveGreaterCommand(collectionManagement));
        commandInvoker.register(CommandName.REMOVE_LOWER, new RemoveLowerCommand(collectionManagement));
        commandInvoker.register(CommandName.REMOVE_ALL_BY_DISTANCE, new RemoveAllByDistance(collectionManagement));
        commandInvoker.register(CommandName.COUNT_GREATER_THAN_DISTANCE, new CountGreaterThanDistanceCommand(collectionManagement));
        commandInvoker.register(CommandName.PRINT_FIELD_ASCENDING_DISTANCE, new PrintFieldAscendingDistanceCommand(collectionManagement));
        commandInvoker.register(CommandName.HELP, new HelpCommand(clientReceiver));
        commandInvoker.register(CommandName.UNDEFINED, new UndefinedCommand(clientReceiver));
        this.commandHandler = new CommandHandler(commandInvoker);
    }

    /**
     * Method for start Client and programme.
     */
    public void start() {
        AcceptingConnections acceptingConnections = new AcceptingConnections(server);
        Socket client = acceptingConnections.connectAccept();
        RequestReading requestReading = new RequestReading(client);
        ReceivedCommand receivedCommand;
        try {
            do {
                receivedCommand = requestReading.readCommand();
                commandHandler.execute(receivedCommand);
            } while (receivedCommand.getCommandName() != CommandName.EXIT);
            commandInvoker.execute(CommandName.SAVE, null);
            OutputDeviceWorker.getOutputDevice().describeString("Completing the connection");
        } catch (IOException e) {
            commandInvoker.execute(CommandName.SAVE, null);
            OutputDeviceWorker.getOutputDevice().describeString("connection is broken");
            start();
        }
        start();
    }


    /**
     * Method for get Collection Management
     *
     * @return Collection Management
     */
    public CollectionManagement getCollectionManagement() {
        return collectionManagement;
    }


    /**
     * Method for get Command Invoker
     *
     * @return Command Invoker
     */
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }


    /**
     * Method for get Execution Depth
     *
     * @return Execution Depth
     */
    public int getExecutionDepth() {
        return executionDepth;
    }

    /**
     * Method for get Command Handler
     *
     * @return Command Handler
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
