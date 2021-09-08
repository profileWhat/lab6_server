package commands;

import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * The command for clear collection
 */
public class ClearCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public ClearCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute Command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cm.clear();
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for remove all element of collection");
    }

}
