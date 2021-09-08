package commands;

import server_management_module.OutputDeviceWorker;
import server_management_module.ServerCommandReceiver;

public class ServerHelpCommand implements Command {
    private final ServerCommandReceiver sCR;

    public ServerHelpCommand(ServerCommandReceiver sCR) {
        this.sCR = sCR;
    }
    @Override
    public void execute(Object object) {
        sCR.help();
    }

    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().describeString("The command for get reference");
    }
}
