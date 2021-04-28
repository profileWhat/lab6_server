package commands;

import serverManagementModule.OutputDeviceWorker;
import collectionManagementModule.CollectionManagement;
import collectionManagementModule.CoupleIdRoute;


/**
 * the Command for update Route by ID to a different Route
 */

public class UpdateCommand implements Command {
    private final CollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public UpdateCommand(CollectionManagement cm) {
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
        OutputDeviceWorker.getOutputDevice().sendMessage("The command for update Route");
    }

}
