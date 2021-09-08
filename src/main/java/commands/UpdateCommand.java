package commands;

import collection_management_module.CoupleIdRoute;
import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;


/**
 * the Command for update Route by ID to a different Route
 */

public class UpdateCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public UpdateCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        if (object instanceof CoupleIdRoute) {
            CoupleIdRoute coupleIdRoute = (CoupleIdRoute) object;
            cm.update(coupleIdRoute);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for update Route");
    }

}
