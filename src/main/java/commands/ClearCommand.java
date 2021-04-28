package commands;

import serverManagementModule.OutputDeviceWorker;
import collectionManagementModule.CollectionManagement;

/**
 * The command for clear collection
 */
public class ClearCommand implements Command {
    private final CollectionManagement cm;

    /**
     * Constructor for load fields
     *
     * @param cm for load to command
     */
    public ClearCommand(CollectionManagement cm) {
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
        OutputDeviceWorker.getOutputDevice().sendMessage("The command for remove all element of collection");
    }

}
