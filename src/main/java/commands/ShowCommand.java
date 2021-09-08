package commands;

import server_management_module.OutputDeviceWorker;
import collection_management_module.RouteCollectionManagement;

/**
 * The command for show elements of collection to file
 */
public class ShowCommand implements Command {
    private final RouteCollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public ShowCommand(RouteCollectionManagement cm) {
        this.cm = cm;
    }

    /**
     * Method for execute command
     *
     * @param object for set argument
     */
    @Override
    public void execute(Object object) {
        cm.show();
    }

    /**
     * Method for describe command
     */
    @Override
    public void describe() {
        OutputDeviceWorker.getOutputDevice().createMessage("The command for show elements of collection to file");
    }

}
