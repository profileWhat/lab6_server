package commands;

import server_management_module.ClientCommandReceiver;
import server_management_module.OutputDeviceWorker;

public class UndefinedCommand implements Command{
    private final ClientCommandReceiver cr;
    /**
     * Constructor for load fields
     *
     * @param cr for load to command
     */
    public UndefinedCommand(ClientCommandReceiver cr) {
        this.cr = cr;
    }

    /**
     * Method for execute Command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cr.notDefine();
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for describe undefined command");
    }

}
