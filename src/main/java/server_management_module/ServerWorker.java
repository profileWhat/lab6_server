package server_management_module;


import collection_management_module.*;
import commands.*;
import data_base.DatabaseCommunicator;
import server_management_module.server_request_reading.ReadingRequestManager;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class of working with the client
 */
public class ServerWorker {
    private final CommandHandler commandHandler;
    private final LoginCollectionManagement loginCollectionManagement;
    private final CommandInvoker commandInvoker;
    private final CommandInvoker serverCommandInvoker;
    private final ServerSocketChannel serverChannel;
    private Boolean exitServerFlag;
    private final AtomicInteger clientNumber;
    private final ExecutorService clientExecutorService;
    private final ExecutorService acceptingConnectionService;
    private final int clientMaxSize = 10;
    /**
     * Constructor of Client Worker. Load all param, init Client Command Receiver, Command Handler to work with input Command, Reader to read Values from file and register Command.
     * @param routeCollectionManagement to load collection to Client Worker
     */
    public ServerWorker(ServerSocketChannel server, RouteCollectionManagement routeCollectionManagement, LoginCollectionManagement loginCollectionManagement) {
        this.clientExecutorService = Executors.newFixedThreadPool(clientMaxSize);
        this.acceptingConnectionService = Executors.newCachedThreadPool();
        this.clientNumber = new AtomicInteger(0);
        this.exitServerFlag = false;
        this.serverChannel = server;
        this.loginCollectionManagement = loginCollectionManagement;
        this.commandInvoker = new CommandInvoker();
        this.serverCommandInvoker = new CommandInvoker();
        ClientCommandReceiver clientReceiver = new ClientCommandReceiver(this);
        ServerCommandReceiver serverCommandReceiver = new ServerCommandReceiver(this);
        serverCommandInvoker.register(CommandName.SERVER_EXIT, new ServerExitCommand(serverCommandReceiver));
        serverCommandInvoker.register(CommandName.SERVER_SHOW_NUMBERS_SIZE, new ServerShowNumbersSizeCommand(serverCommandReceiver));
        serverCommandInvoker.register(CommandName.SERVER_HELP, new ServerHelpCommand(serverCommandReceiver));
        commandInvoker.register(CommandName.INFO, new InfoCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.SHOW, new ShowCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.ADD, new AddCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.UPDATE, new UpdateCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.REMOVE_BY_ID, new RemoveByIdCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.CLEAR, new ClearCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand(clientReceiver));
        commandInvoker.register(CommandName.EXIT, new ExitCommand(clientReceiver));
        commandInvoker.register(CommandName.REMOVE_FIRST, new RemoveFirstCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.REMOVE_GREATER, new RemoveGreaterCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.REMOVE_LOWER, new RemoveLowerCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.REMOVE_ALL_BY_DISTANCE, new RemoveAllByDistance(routeCollectionManagement));
        commandInvoker.register(CommandName.COUNT_GREATER_THAN_DISTANCE, new CountGreaterThanDistanceCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.PRINT_FIELD_ASCENDING_DISTANCE, new PrintFieldAscendingDistanceCommand(routeCollectionManagement));
        commandInvoker.register(CommandName.HELP, new HelpCommand(clientReceiver));
        commandInvoker.register(CommandName.UNDEFINED, new UndefinedCommand(clientReceiver));
        this.commandHandler = new CommandHandler(commandInvoker, routeCollectionManagement);
    }
    public void startWithThread() {
        AcceptingConnections AC = new AcceptingConnections(serverChannel);
        Callable<SocketChannel> acceptingConnectionTask = AC::connectAccept;
        Runnable serverInteraction = () -> {
            Scanner scanner = new Scanner(System.in);
            while(!exitServerFlag && DatabaseCommunicator.getDatabaseCommunicator().isWorkingDB()) {
                String string = scanner.next();
                try {
                    serverCommandInvoker.execute(CommandName.valueOf(ServerCommandName.valueOf(string.toUpperCase()).toString()), null);
                } catch (IllegalArgumentException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Undefined command can't be executed \n");
                }
            }
        };
        new Thread(serverInteraction).start();
        while (!exitServerFlag && DatabaseCommunicator.getDatabaseCommunicator().isWorkingDB()) {
            try {
                SocketChannel client = acceptingConnectionService.submit(acceptingConnectionTask).get();
                clientNumber.incrementAndGet();
                Runnable task = () -> {
                    try {
                        OutputDeviceWorker.getOutputDevice().setOutputStream(client.socket().getOutputStream());
                        ReadingRequestManager rRM = new ReadingRequestManager(client);
                        AuthorizationAndRegistrationWorker loginAndRegistration = new AuthorizationAndRegistrationWorker(rRM, loginCollectionManagement, client);
                        String userName = loginAndRegistration.login();
                        while (!exitServerFlag) {
                            try {
                                commandHandler.execute(rRM.getClientCommand(), userName);
                            } catch (IOException e) {
                                OutputDeviceWorker.getOutputDevice().describeString("Connection with " + client.getRemoteAddress() + " is broken");
                                break;
                            }
                            OutputDeviceWorker.getOutputDevice().sendMessage(client);
                        }

                    } catch (IOException e) {
                        OutputDeviceWorker.getOutputDevice().describeString("Connection with client cant be establish");
                    }
                    clientNumber.decrementAndGet();
                };
                if (clientNumber.get() > clientMaxSize) {
                    try {
                        clientNumber.decrementAndGet();
                        OutputDeviceWorker.getOutputDevice().setOutputStream(client.socket().getOutputStream());
                        OutputDeviceWorker.getOutputDevice().createMessage("The client limit has been exceeded, please connect later ");
                        OutputDeviceWorker.getOutputDevice().setEndOfClientFlag();
                        OutputDeviceWorker.getOutputDevice().sendMessage();
                        OutputDeviceWorker.getOutputDevice().describeString("Connection with " + client.getRemoteAddress() + " is broken");
                    } catch (IOException|NullPointerException e) {
                        OutputDeviceWorker.getOutputDevice().describeString("Connection with client cant be establish");
                    }
                } else if (!exitServerFlag) clientExecutorService.submit(task);
                } catch (InterruptedException|ExecutionException e) {
                    System.out.println("Connection with client cant be establish");
                }
        }
        clientExecutorService.shutdown();
        acceptingConnectionService.shutdown();
        OutputDeviceWorker.getOutputDevice().describeString("The server is down");
    }

    /**
     * Method for get Command Invoker
     *
     * @return Command Invoker
     */
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }

    public void setExitServerFlag() {
        this.exitServerFlag = true;
    }

    public AtomicInteger getClientNumber() {
        return clientNumber;
    }

    public CommandInvoker getServerCommandInvoker() {
        return serverCommandInvoker;
    }

    public ExecutorService getAcceptingConnectionService() {
        return acceptingConnectionService;
    }

    public ExecutorService getClientExecutorService() {
        return clientExecutorService;
    }
}
