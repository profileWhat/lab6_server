package fileManagementModule;

import serverManagementModule.OutputDeviceWorker;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import collectionManagementModule.*;

import java.util.PriorityQueue;

/**
 * Class for work de/deserialize json file
 */
public class JsonWorker {
    private static JsonWorker jsonWorker;
    private final Gson gson;
    private boolean IsIncorrectJsonFile;

    private JsonWorker() {
        IsIncorrectJsonFile = false;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(Expose.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> c) {
                        return false;
                    }
                })
                .create();
    }

    /**
     * Static Method to init Json Worker for the first time and then get this Json Worker.
     *
     * @return Json Worker
     */
    public static JsonWorker getJsonWorker() {
        if (JsonWorker.jsonWorker == null) JsonWorker.jsonWorker = new JsonWorker();
        return JsonWorker.jsonWorker;

    }

    /**
     * Method for deserialize file to Route Array
     *
     * @return Array of route
     */
    public Route[] deserializeToRouteArray() {
        Route[] routes = null;
        try {
            String json = FileWorker.getFileWorker().read();
            routes = gson.fromJson(json, Route[].class);
        } catch (JsonSyntaxException e) {
            IsIncorrectJsonFile = true;
            OutputDeviceWorker.getOutputDevice().createMessage("The json file is not working properly, now you are working with an empty collection");
        }
        return routes;
    }

    /**
     * Method for serialize collection to file
     *
     * @param collection for serialize it to file
     */
    public void serializeCollectionToFile(PriorityQueue<Route> collection) {
        if (!IsIncorrectJsonFile) {
            String json = gson.toJson(collection);
            FileWorker.getFileWorker().write(json);
        }
    }

    /**
     * Method for return is incorrect json file flag
     *
     * @return is incorrect json file flag
     */
    public boolean isIncorrectJsonFile() {
        return IsIncorrectJsonFile;
    }
}
