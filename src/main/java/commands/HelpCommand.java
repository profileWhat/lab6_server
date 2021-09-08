package commands;

import server_management_module.ClientCommandReceiver;
import server_management_module.OutputDeviceWorker;

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
        OutputDeviceWorker.getOutputDevice().createMessage("The command for get reference");
    }

}
