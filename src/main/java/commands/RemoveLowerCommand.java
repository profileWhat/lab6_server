package commands;

import serverManagementModule.OutputDeviceWorker;
import collectionManagementModule.CollectionManagement;
import collectionManagementModule.Route;

/**
 * Command for remove all element Lower than current Route
 */
public class RemoveLowerCommand implements Command {
    private final CollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public RemoveLowerCommand(CollectionManagement cm) {
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
            cm.removeLower(route);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().sendMessage("The command for remove all element Lower current Route");
    }

}
