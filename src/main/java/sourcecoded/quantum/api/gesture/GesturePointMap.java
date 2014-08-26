package sourcecoded.quantum.api.gesture;

import java.util.ArrayList;

/**
 * A Class used to generate PointMaps of Gestures.
 *
 * This is used for comparing gestures to those with predefined
 * paths, with a certain level of tolerance as well as a duration
 *
 * This functions with Pitch and Yaw from the players' camera
 *
 * If this is implemented in your own custom path, be sure
 * to generate the path using the length of the one you
 * are given, as each point it compared to the corresponding
 * one on the other map. In other words, make sure the path
 * you compare to is the same length
 */
@SuppressWarnings("NullableProblems")
public class GesturePointMap {

    ArrayList<Float> xMap = new ArrayList<Float>();
    ArrayList<Float> yMap = new ArrayList<Float>();
    int length;             //Because reasons
    float tolerance = 0.0F;

    public float maxX, maxY;
    public float minX, minY;

    /**
     * Create a new Gesture Point Map
     */
    public GesturePointMap() {
        length = 0;
    }

    /**
     * Set the tolerance of this gesture map
     */
    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * Add a point to this map
     */
    public void addPoint(float x, float y) {
        length++;
        xMap.add(x);
        yMap.add(y);

        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);

        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
    }

    /**
     * Get the total amount of recorded points in this map.
     * This corresponds directly to the amount of ticks
     * this gesture took to complete
     */
    public int getLength() {
        return length;
    }

    /**
     * Compare this map to another map
     *
     * @return 0 if it doesn't match, 1 if it does.
     * Will return -1 if the arrays are not the same size (can't be compared)
     */
    public int compareTo(GesturePointMap compared) {
        if (length != compared.getLength()) return -1;

        float maxTolerance = Math.max(tolerance, compared.tolerance);                //Makes sure we're both happy here

        for (int i = 0; i < length; i++) {
            float xDiff = Math.abs(compared.xMap.get(i)) - Math.abs(xMap.get(i));
            float yDiff = Math.abs(compared.yMap.get(i)) - Math.abs(yMap.get(i));

            if (!((-maxTolerance <= xDiff && xDiff <= maxTolerance) && (-maxTolerance <= yDiff && yDiff <= maxTolerance)))
                return 0;  //Messy, I know
        }

        return 1;
    }

    /**
     * Convert this to a string. Mostly used for debugging
     */
    public String toString() {
        ArrayList<String> steps = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            steps.add(String.format("[%s, %s]", xMap.get(i), yMap.get(i)));
        }
        return steps.toString();
    }
}
