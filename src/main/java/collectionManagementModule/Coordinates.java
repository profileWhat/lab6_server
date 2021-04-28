package collectionManagementModule;

import java.io.Serializable;

/**
 * Coordinate class
 */
public class Coordinates implements Serializable {
    private final int x; //Максимальное значение поля: 546
    private final float y;

    /**
     * Constructor for load fields
     *
     * @param x for load to coordinates
     * @param y for load to coordinates
     */
    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method for get Y
     *
     * @return Y
     */
    public float getY() {
        return y;
    }

    /**
     * Method for get X
     *
     * @return X
     */
    public int getX() {
        return x;
    }
}
