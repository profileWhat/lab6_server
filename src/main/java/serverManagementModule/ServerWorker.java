package serverManagementModule;


import collectionManagementModule.*;
import commands.*;

import java.nio.channels.Selector;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.nio.channels.SelectionKey.*;

/**
 * Class of working with the client
 */
public class ServerWorker {
    private final CommandHandler commandHandler;
    private final CollectionManagement collectionManagement;
    private final CommandInvoker commandInvoker;
    private final ServerSocketChannel serverChannel;
    /**
     * Constructor of Client Worker. Load all param, init Client Command Receiver, Command Handler to work with input Command, Reader to read Values from file and register Command.
     * @param collectionManagement to load collection to Client Worker
     */
    public ServerWorker(ServerSocketChannel server, CollectionManagement collectionManagement) {
        this.serverChannel = server;
        this.collectionManagement = collectionManagement;
        this.commandInvoker = new CommandInvoker();
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
     * Method for start Client and programme. That method will working endless while server won't stop working.
     * That method work with Selector and polls all clients connected to the server.
     */
    public void start() {
        try {
            Selector sel = Selector.open();
            serverChannel.configureBlocking(false);
            serverChannel.register(sel, OP_ACCEPT);
            while(true) {
                int readyChannels = sel.selectNow();
                if(readyChannels == 0) continue;
                Set<SelectionKey> keys = sel.selectedKeys();
                Iterator keyIterator = keys.iterator();
                sel.select();
                while (keyIterator.hasNext()) {
                    SelectionKey currentKey = (SelectionKey) keyIterator.next();
                    if (currentKey.isValid()) {
                        if (currentKey.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) currentKey.channel();
                            AcceptingConnections acceptingConnections = new AcceptingConnections(serverChannel);
                            SocketChannel socketChannel = acceptingConnections.connectAccept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(currentKey.selector(), OP_READ);
                            keyIterator.remove();
                        }
                        if (currentKey.isReadable()) {
                            try {
                                SocketChannel socketChannel = (SocketChannel) currentKey.channel();
                                RequestReading requestReading = new RequestReading(socketChannel);
                                commandHandler.execute(requestReading.readCommand());
                                socketChannel.configureBlocking(false);
                                socketChannel.register(currentKey.selector(), OP_WRITE);
                                keyIterator.remove();
                            } catch (IOException e) {
                                OutputDeviceWorker.getOutputDevice().describeString("Connection with " + ((SocketChannel) currentKey.channel()).getRemoteAddress() + " is broken");
                                keyIterator.remove();
                                currentKey.cancel();
                                continue;
                            }
                        }
                        if (currentKey.isWritable()) {
                            SocketChannel socketChannel = (SocketChannel) currentKey.channel();
                            keyIterator.remove();
                            currentKey.cancel();
                            socketChannel.configureBlocking(true);
                            OutputDeviceWorker.getOutputDevice().setOutputStream(socketChannel.socket().getOutputStream());
                            OutputDeviceWorker.getOutputDevice().sendMessage();
                            socketChannel.configureBlocking(false);
                            sel.selectNow();
                            socketChannel.register(currentKey.selector(), OP_READ);
                        }
                    }
                }
            }
        } catch (IOException e) {
           OutputDeviceWorker.getOutputDevice().describeString("connection is broken");
        }
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


}
