package commands;

import server_management_module.ClientCommandReceiver;
import server_management_module.OutputDeviceWorker;


/**
 * The command for execute script
 */
public class ExecuteScriptCommand implements Command {
    private final ClientCommandReceiver cr;

    /**
     * Constructor for load fields
     *
     * @param cr for load to command
     */
    public ExecuteScriptCommand(ClientCommandReceiver cr) {
        this.cr = cr;
    }

    /**
     * Method for execute command
     *
     * @param object to set argument
     */
    @Override
    public void execute(Object object) {
        if (object instanceof String)  {
            String fileName = (String) object;
            cr.executeScript(fileName);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for read and execute the script from the specified file");
    }

}
