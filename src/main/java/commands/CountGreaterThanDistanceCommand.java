package commands;

import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * The command for count element witch have distance greater than input distance
 */

public class CountGreaterThanDistanceCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public CountGreaterThanDistanceCommand(RouteCollectionManagement cm) {
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
            cm.countGreaterThanDistance(distance);
        }
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for count element greater than input distance");
    }

}
