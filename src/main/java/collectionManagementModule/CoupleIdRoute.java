package collectionManagementModule;

import java.io.Serializable;

/**
 * Couple Id-Route class
 */
public class CoupleIdRoute implements Serializable {
    private final Long id;
    private final Route route;

    /**
     * Construcor for load id and route
     *
     * @param id    to load to couple
     * @param route to load to route
     */
    public CoupleIdRoute(Long id, Route route) {
        this.id = id;
        this.route = route;
    }

    /**
     * Method for get Id
     *
     * @return Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Method for get Route
     *
     * @return Route
     */
    public Route getRoute() {
        return route;
    }
}
