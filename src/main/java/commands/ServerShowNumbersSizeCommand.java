package commands;

import server_management_module.OutputDeviceWorker;
import server_management_module.ServerCommandReceiver;

public class ServerShowNumbersSizeCommand implements Command{
    private final ServerCommandReceiver sCR;
    public ServerShowNumbersSizeCommand(ServerCommandReceiver sCR) {
        this.sCR = sCR;
    }
    @Override
    public void execute(Object object) {
        sCR.show_numbers_size();
    }

    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().describeString("The command to show the number of clients on the server");
    }
}
