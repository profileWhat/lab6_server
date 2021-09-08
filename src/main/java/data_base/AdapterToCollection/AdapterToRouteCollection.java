package data_base.AdapterToCollection;

import collection_management_module.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdapterToRouteCollection implements BDtoCollectionAdapter{
    private final Statement statementBD;
    private final RouteCollectionManagement routeCollectionManagement;
    public AdapterToRouteCollection(Statement statementBD, RouteCollectionManagement routeCollectionManagement) {
        this.statementBD = statementBD;
        this.routeCollectionManagement = routeCollectionManagement;
    }

    @Override
    public void adapt() throws SQLException {
        ResultSet routeInfo = statementBD.executeQuery("SELECT * FROM routes");
        while (routeInfo.next()) {
            LocationFrom locationFrom = new LocationFrom(
                    routeInfo.getInt("locationfromcoordx"),
                    routeInfo.getLong("locationfromcoordy"),
                    routeInfo.getInt("locationfromcoordz"));
            LocationTo locationTo = new LocationTo(
                    routeInfo.getInt("locationtocoordx"),
                    routeInfo.getDouble("locationtocoordy"),
                    routeInfo.getInt("locationtocoordz"),
                    routeInfo.getString("locationtoname"));
            Coordinates coordinates = new Coordinates(
                    routeInfo.getInt("routecoordinatex"),
                    routeInfo.getFloat("routecoordinatey"));
            Route route = new Route(
                    routeInfo.getString("routename"),
                    coordinates,
                    locationFrom,
                    locationTo);
            route.setCreationDate(routeInfo.getDate("creationdate"));
            route.setId(routeInfo.getLong("routeid"));
            route.setRouteCreator(routeInfo.getString("routecreator"));
            routeCollectionManagement.addRoute(route);
        }
    }
}
