package commands;

import serverManagementModule.OutputDeviceWorker;
import collectionManagementModule.CollectionManagement;

/**
 * The command for remove all element by distance
 */
public class RemoveAllByDistance implements Command {
    private final CollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public RemoveAllByDistance(CollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        if (object instanceof Double)  {
            Double distance = (Double) object;
            cm.removeAllByDistance(distance);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().sendMessage("The command for remove all element by distance");
    }

}
