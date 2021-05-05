package commands;

import serverManagementModule.ClientCommandReceiver;
import serverManagementModule.OutputDeviceWorker;

/**
 * The command for finish programme
 */
public class ExitCommand implements Command {
    ClientCommandReceiver cr;

    /**
     * Constructor for load fields
     *
     * @param cr for load to command
     */
    public ExitCommand(ClientCommandReceiver cr) {
        this.cr = cr;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cr.exit();
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for exit complete the program");
    }

}
