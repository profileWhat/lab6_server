package commands;

import collection_management_module.Route;
import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * the Command for add Route to collection
 */
public class AddCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public AddCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute Command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        if (object instanceof Route) {
            Route route = (Route) object;
            cm.add(route);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for add route to collection");
    }

}
