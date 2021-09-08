package commands;

import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * The command for remove first element of collection
 */
public class RemoveFirstCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public RemoveFirstCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cm.removeFirst();
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for remove first element of collection");
    }

}
