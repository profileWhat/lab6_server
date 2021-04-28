package collectionManagementModule;

import java.io.Serializable;

/**
 * Location to class
 */
public class LocationTo implements Serializable {
    private final long x;
    private final double y;
    private final Integer z; //Поле не может быть null
    private final String name; //Поле не может быть null

    /**
     * Constructor for load fields
     *
     * @param x    for load to location to
     * @param y    for load to location to
     * @param z    for load to location to
     * @param name for load to location to
     */
    public LocationTo(long x, double y, Integer z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Method for get X
     *
     * @return X
     */
    public long getX() {
        return x;
    }

    /**
     * Method for get Y
     *
     * @return Y
     */
    public double getY() {
        return y;
    }

    /**
     * Method for get Z
     *
     * @return Z
     */
    public Integer getZ() {
        return z;
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
