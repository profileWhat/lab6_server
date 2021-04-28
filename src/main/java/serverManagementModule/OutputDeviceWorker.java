package serverManagementModule;

import collectionManagementModule.Route;

import java.io.*;

/**
 * Class for working with the input device
 */
public class OutputDeviceWorker {
    private static OutputDeviceWorker describer;
    private OutputStream outputStream;
    private OutputDeviceWorker() {

    }

    public void setOutputStream(OutputStream out) {
        this.outputStream = out;
    }
    /**
     * Static Method to init Output Device for the first time and then get this Output Device.
     *
     * @return Output Device
     */
    public static OutputDeviceWorker getOutputDevice() {
        if (OutputDeviceWorker.describer == null) OutputDeviceWorker.describer = new OutputDeviceWorker();
        return OutputDeviceWorker.describer;
    }


    /**
     * Method for describe file which not specified
     */
    public void describeFileNotSpecified() {
        System.out.println("the environment variable is not set, the collection will not be loaded and will not be saved");
    }

    public void describeString(String s) {
        System.out.println(s);
    }
    /**
     * Method for describe String
     *
     * @param message to describe string
     */
    public void sendMessage(String message) {
        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Message can't be sent, haven't got connection");
        }
    }

    public void sendEndOfScriptExFlag() {
        try {
            byte[] bytes = {1};
            outputStream.write(bytes);
        } catch (IOException e) {
            System.out.println("Message can't be sent, haven't got connection");
        }
    }
    /**
     * Method for describe Exception
     *
     * @param e to describe exception
     */
    public void describeException(Exception e) {
        System.out.println(e.getMessage());
    }

    /**
     * Method to describe Collection Info
     *
     * @param collectionClassName to describe collection class name
     * @param creationDate        to describe collection date creation
     * @param collectionSize      to describe collection size
     */
    public void describeCMInfo(String collectionClassName, java.util.Date creationDate, int collectionSize) {
        String message =
                "Type of collection: " + collectionClassName + '\n' +
                        "Date of creation: " + creationDate + '\n' +
                        "Size of collection: " + collectionSize + '\n';
        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Message can't be sent, haven't got connection");
        }
    }

    /**
     * Method for describe Distance
     *
     * @param distance to describe Distance
     */
    public void describeDistance(Double distance) {
        try {
            outputStream.write(distance.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Message can't be sent, haven't got connection");
        }
    }

    /**
     * Method for show Route
     *
     * @param route to show it
     */
    public void showRoute(Route route) {
        String message = '\n' +
                "Route Name: " + route.getName() + '\n' +
                        "Id: " + route.getId() + '\n' +
                        "Coordinates: " + '\n' +
                        "\t x: " + route.getCoordinates().getX() + '\n' +
                        "\t y: " + route.getCoordinates().getY() + '\n' +
                        "CreationDate: " + route.getCreationDate() + '\n' +
                        "LocationFrom: " + '\n' +
                        "\t x: " + route.getFrom().getX() + '\n' +
                        "\t y: " + route.getFrom().getY() + '\n' +
                        "\t z: " + route.getFrom().getZ() + '\n' +
                        "LocationTo: " + '\n' +
                        "\t x: " + route.getTo().getX() + '\n' +
                        "\t y: " + route.getTo().getY() + '\n' +
                        "\t z: " + route.getTo().getZ() + '\n' +
                        "\t name: " + route.getTo().getName() + '\n' +
                        "Distance: " + route.getDistance() +'\n';
        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Message can't be sent, haven't got connection");
        }
    }
}
