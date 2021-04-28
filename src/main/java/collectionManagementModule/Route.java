package collectionManagementModule;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 * Route class
 */
public class Route implements Serializable {
    @Expose
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    @Expose
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final LocationFrom from; //Поле может быть null
    private final LocationTo to; //Поле может быть null
    private final Double distance; //Поле может быть null, Значение поля должно быть больше 1

    /**
     * Constructor for load and init fields
     *
     * @param name        for load to route
     * @param coordinates for load to route
     * @param from        for load to route
     * @param to          for load to route
     */
    public Route(String name, Coordinates coordinates, LocationFrom from, LocationTo to) {
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = from != null && to != null ? Math.sqrt(Math.pow(from.getX().longValue() - to.getX(), 2)
                + Math.pow(from.getY().doubleValue() - to.getY(), 2)
                + Math.pow(from.getZ() - to.getZ(), 2)) : null;
        this.creationDate = new java.util.Date();
    }

    /**
     * Method for set creation Date
     */
    public void setCreationDate() {
        this.creationDate = new java.util.Date();
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
     * Method for set Id
     *
     * @param id to load it
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method for get Distance
     *
     * @return Distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Method for get Coordinates
     *
     * @return Coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Method for get Creation Date
     *
     * @return Creation Cate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Method for get Location From
     *
     * @return Location Form
     */
    public LocationFrom getFrom() {
        return from;
    }

    /**
     * Method for get Location To
     *
     * @return Location To
     */
    public LocationTo getTo() {
        return to;
    }

    /**
     * Method for get Name
     *
     * @return Name
     */
    public String getName() {
        return name;
    }
}
