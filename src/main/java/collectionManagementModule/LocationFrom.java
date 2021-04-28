package collectionManagementModule;

import java.io.Serializable;

/**
 * Location from class
 */
public class LocationFrom implements Serializable {
    private final Integer x; //Поле не может быть null
    private final Long y; //Поле не может быть null
    private final int z;

    /**
     * Constructor for load fields
     *
     * @param x for load to location from
     * @param y for load to location from
     * @param z for load to location from
     */
    public LocationFrom(Integer x, Long y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Method for get X
     *
     * @return X
     */
    public Integer getX() {
        return x;
    }

    /**
     * Method for get Y
     *
     * @return Y
     */
    public Long getY() {
        return y;
    }

    /**
     * Method for get Z
     *
     * @return Z
     */
    public int getZ() {
        return z;
    }
}
