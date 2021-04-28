package commands;

import serverManagementModule.ClientCommandReceiver;
import serverManagementModule.OutputDeviceWorker;

/**
 * The command for get reference
 */
public class HelpCommand implements Command {
    private final ClientCommandReceiver cr;

    /**
     * Constructor for load fields
     *
     * @param cr for load to command
     */
    public HelpCommand(ClientCommandReceiver cr) {
        this.cr = cr;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cr.help();
    }


    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().sendMessage("The command for get reference");
    }

}
