package commands;

import server_management_module.OutputDeviceWorker;
import server_management_module.ServerCommandReceiver;

public class ServerExitCommand implements Command{
    private final ServerCommandReceiver sCR;

    public ServerExitCommand(ServerCommandReceiver sCR) {
        this.sCR = sCR;
    }

    @Override
    public void execute(Object object) {
        sCR.exit();
    }

    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().describeString("The command to exit server");
    }
}
