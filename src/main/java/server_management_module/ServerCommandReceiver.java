package server_management_module;

import commands.Command;
import commands.CommandName;
import commands.ServerCommandName;

public class ServerCommandReceiver {
    private final ServerWorker server;

    public ServerCommandReceiver(ServerWorker server) {
        this.server = server;
    }

    public void exit() {
        server.setExitServerFlag();
        server.getAcceptingConnectionService().shutdownNow();
        server.getClientExecutorService().shutdownNow();
    }

    public void show_numbers_size() {
        OutputDeviceWorker.getOutputDevice().describeString(server.getClientNumber().get() + ": clients on server");
    }

    public void help() {
        for (ServerCommandName serverCommandName: ServerCommandName.values()) {
            String message;
            Command command = server.getServerCommandInvoker().getCommandMap().get(CommandName.valueOf(serverCommandName.name()));
            message = serverCommandName.toString().toLowerCase() + ": ";
            OutputDeviceWorker.getOutputDevice().describeString(message);
            command.describe();
            OutputDeviceWorker.getOutputDevice().describeString("");
        }
    }
}
