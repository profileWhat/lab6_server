package collection_management_module;

import java.sql.SQLException;
import java.util.*;

import data_base.DBWorker;
import server_management_module.OutputDeviceWorker;

/**
 * This class have a part of Receiver class
 * <p>
 * Class for working with a collection
 */

public class RouteCollectionManagement {
    private final PriorityQueue<Route> collection;
    private final Date creationDate;
    private final DBWorker dbWorker;
    private String userName;
    /**
     * Constructor for init collection, creation date, hash set of id routes.
     */
    public RouteCollectionManagement(DBWorker dbWorker) {
        this.collection = new PriorityQueue<>(
                Comparator.comparingInt(route -> route.getId().intValue()));
        this.creationDate = new Date();
        this.dbWorker = dbWorker;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method for add routes
     */
    public void addRoute(Route route) {
        collection.add(route);
    }

    /**
     * Method for add route
     *
     * @param route for add it
     */
    public void add(Route route) {
        if (route.getDistance() < 1) {
            OutputDeviceWorker.getOutputDevice().createMessage("The route distance is less than 1, the route cannot be added");
        } else {
            try {
                dbWorker.addRouteWithId(route);
                collection.add(route);
                OutputDeviceWorker.getOutputDevice().createMessage("Route added \n");
            } catch (SQLException e) {
                OutputDeviceWorker.getOutputDevice().createMessage("Route have not added");
            }
        }
    }

    /**
     * Method for update route by Id and input Route
     *
     * @param coupleIdRoute for update by Id and input Route
     */
    public void update(CoupleIdRoute coupleIdRoute) {
        boolean executedFlag = false;
        for (Route route: collection) {
            if (route.getId().equals(coupleIdRoute.getId()) && userName.equals(route.getRouteCreator())) {
                coupleIdRoute.getRoute().setId(coupleIdRoute.getId());
                try {
                    dbWorker.updateRoute(coupleIdRoute.getRoute());
                    collection.remove(route);
                    collection.add(coupleIdRoute.getRoute());
                    executedFlag = true;
                    OutputDeviceWorker.getOutputDevice().createMessage("Route updated \n");
                    break;
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Route can't update");
                }
            }
        }
        if (!executedFlag) OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got element with input id or you don't have enough rights to modify the route\n");
    }

    /**
     * Method for get info about collection
     */
    public void info() {
        String message =
                "Type of collection: " + collection.getClass().getName() + '\n' +
                        "Date of creation: " + creationDate + '\n' +
                        "Size of collection: " + collection.size() + '\n';
        OutputDeviceWorker.getOutputDevice().createMessage(message);
    }

    /**
     * Method for remove route by id
     *
     * @param id for remove route by it
     */
    public void removeById(Long id) {
        if (collection.removeIf(route -> (id.equals(route.getId()) && userName.equals(route.getRouteCreator())))) {
            try {
                dbWorker.removeById(id);
                OutputDeviceWorker.getOutputDevice().createMessage("Route by id deleted \n");
            } catch (SQLException e) {
                OutputDeviceWorker.getOutputDevice().createMessage("Route can't be deleted \n");
            }
        } else OutputDeviceWorker.getOutputDevice().createMessage("Route by id not found, it can't be deleted or you don't have enough rights to modify the route\n");

    }

    /**
     * Method for clear collection
     */
    public void clear() {
        for (Route route:collection) {
            if (userName.equals(route.getRouteCreator())) {
                try {
                    dbWorker.removeById(route.getId());
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Routes can't be cleared");
                    break;
                }
            }
        }
        collection.removeIf(route -> (userName.equals(route.getRouteCreator())));
        OutputDeviceWorker.getOutputDevice().createMessage("All routes created by you have been deleted\n");
    }

    /**
     * Method for remove first route of collection
     */
    public void removeFirst() {
        boolean executedFlag = false;
        for (Route route:collection) {
            if (userName.equals(route.getRouteCreator())) {
                try {
                    dbWorker.removeById(route.getId());
                    collection.remove(route);
                    OutputDeviceWorker.getOutputDevice().createMessage("The first element you entered has been deleted \n");
                    executedFlag = true;
                    break;
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Route can't be removed");
                }
            }
        }
        if (!executedFlag) OutputDeviceWorker.getOutputDevice().createMessage("The elements you entered do not exist in the collection \n");
    }

    /**
     * Method for remove routes greater input route
     *
     * @param currentRoute for delete routes lower than it
     */
    public void removeGreater(Route currentRoute) {
        for(Route route: collection) {
            if (route.getDistance() < currentRoute.getDistance() && userName.equals(route.getRouteCreator())) {
                try {
                    dbWorker.removeById(route.getId());
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Route can't be removed");
                }
            }
        }
        if (collection.size() != 0) {
            if (collection.removeIf(route -> (currentRoute.getDistance() < route.getDistance() && userName.equals(route.getRouteCreator())))) {
                OutputDeviceWorker.getOutputDevice().createMessage("Remove all element greater than input element \n");
            } else
                OutputDeviceWorker.getOutputDevice().createMessage("Input route so small, elements of collection weren't deleted \n");
        } else
            OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got elements, can't remove greater element \n");
    }

    /**
     * Method for remove routes lower input route
     *
     * @param currentRoute for delete routes greater than it
     */
    public void removeLower(Route currentRoute) {
        for(Route route: collection) {
            if (route.getDistance() > currentRoute.getDistance() && userName.equals(route.getRouteCreator())) {
                try {
                    dbWorker.removeById(route.getId());
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Route can't be removed");
                }
            }
        }
        if (collection.size() != 0) {
            if (collection.removeIf(route -> (currentRoute.getDistance() > route.getDistance())))
                OutputDeviceWorker.getOutputDevice().createMessage("Remove all element lower than input element \n");
            else
                OutputDeviceWorker.getOutputDevice().createMessage("Input route so high, elements of collection weren't deleted \n");
        } else
            OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got elements, can't remove lower element \n");
    }

    /**
     * Method for remove routes by input distance
     *
     * @param currentDistance for delete routes by it
     */
    public void removeAllByDistance(Double currentDistance) {
        for(Route route: collection) {
            if (route.getDistance().equals(currentDistance) && userName.equals(route.getRouteCreator())) {
                try {
                    dbWorker.removeById(route.getId());
                } catch (SQLException e) {
                    OutputDeviceWorker.getOutputDevice().describeString("Route can't be removed");
                }
            }
        }
        if (collection.size() != 0) {
            if (collection.removeIf(route -> (currentDistance == route.getDistance().doubleValue())))
                OutputDeviceWorker.getOutputDevice().createMessage("Remove all element equal by distance \n");
            else OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got element with input distance \n");
        } else
            OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got elements, cant remove elements \n");
    }

    /**
     * Method for count routes greater than input distance
     *
     * @param distance for count routes greater than it
     */
    public void countGreaterThanDistance(Double distance) {
        if (collection.size() != 0) {
            long count = 0L;
            for (Route currentRoute : collection) {
                if (currentRoute.getDistance() > distance) {
                    count++;
                }
            }
            OutputDeviceWorker.getOutputDevice().createMessage("Elements with distance greater than input distance found" + count +"\n");
        } else
            OutputDeviceWorker.getOutputDevice().createMessage("Collection haven't got elements, cant count elements with distance greater than input distance \n");
    }

    /**
     * Method for print field ascending distance
     */
    public void printFieldAscendingDistance() {
        if (collection.size() != 0) {
            PriorityQueue<Route> pqCompareByDistance = new PriorityQueue<>(
                    Comparator.comparingDouble(Route::getDistance));
            pqCompareByDistance.addAll(collection);
            StringBuilder stringBuilder = new StringBuilder();
            for (Route route : pqCompareByDistance) {
                stringBuilder.append(route.getDistance());
                stringBuilder.append("\n");
            }
            OutputDeviceWorker.getOutputDevice().createMessage(stringBuilder.toString());
        } else OutputDeviceWorker.getOutputDevice().createMessage("Collection is Empty, command can't be executed \n");
    }


    /**
     * Method to show routes
     */
    public void show() {
        if (collection.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Route route : collection) {
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
                        "Distance: " + route.getDistance() +'\n' +
                        "RouteCreator: " + route.getRouteCreator() + '\n';
                stringBuilder.append(message);
            }
            OutputDeviceWorker.getOutputDevice().createMessage(stringBuilder.toString());
        } else OutputDeviceWorker.getOutputDevice().createMessage("Collection is Empty + \n");

    }
}

