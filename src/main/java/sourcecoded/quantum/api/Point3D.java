package sourcecoded.quantum.api;

/**
 * This class is used in the Arrangement Table's
 * algorithm, as javafx.geometry is not available
 * in Java 1.6.
 *
 * @author SourceCoded
 */
public class Point3D {

    int x, y, z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

}
