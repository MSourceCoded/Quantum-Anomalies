package sourcecoded.quantum.api.gesture;

import java.awt.*;
import java.util.ArrayList;

public class GesturePointMap {

    ArrayList<Float> xMap = new ArrayList<Float>();
    ArrayList<Float> yMap = new ArrayList<Float>();

    public int length;

    public float maxX, maxY;
    public float minX, minY;

    public void addPoint(float x, float y) {
        length ++;

        xMap.add(x);
        yMap.add(y);

        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);

        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
    }

    public int length() {
        return length;
    }

    public Point getPoint(int index) {
        return new Point(xMap.get(index).intValue(), yMap.get(index).intValue());
    }

    /**
     * Convert this to a string. Mostly used for debugging
     */
    public String toString() {
        ArrayList<String> steps = new ArrayList<String>();
        for (int i = 0; i < xMap.size(); i++) {
            steps.add(String.format("[%s, %s]", xMap.get(i), yMap.get(i)));
        }
        return steps.toString();
    }
}
