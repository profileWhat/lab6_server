package commands;

import collection_management_module.Route;
import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * The command for remove all element Greater than current Route
 */
public class RemoveGreaterCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public RemoveGreaterCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        if (object instanceof Route) {
            Route route = (Route) object;
            cm.removeGreater(route);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for remove all element Greater current Route");
    }

}
