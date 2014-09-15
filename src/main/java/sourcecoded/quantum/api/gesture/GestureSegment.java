package sourcecoded.quantum.api.gesture;

import java.awt.*;

public class GestureSegment {

    public Point p1;
    public Point p2;

    public int tolerance;

    public boolean locked = false;
    public boolean completed = false;

    public GestureSegment() {}
    public GestureSegment(Point p1, Point p2, int tolerance) {
        this.p1 = p1;
        this.p2 = p2;

        this.tolerance = tolerance;
    }

    public void lock() {
        locked = true;
    }

    public void complete() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean getLocked() {
        return locked;
    }

    public Point getPoint1() {
        return p1;
    }

    public Point getPoint2() {
        return p2;
    }

    public int getTolerance() {
        return tolerance;
    }

    public boolean isPointInBounds(Point p) {
        return getBounding().contains(p);
    }

    public Polygon getBounding() {
        double rot;
        if (p1.x == p2.x)
            rot = 0D;
        else if (p1.y == p2.y)
            rot = 90D;
        else
            rot = 180D - 45D / ((p2.y - p1.y) / (p2.x - p1.x));

        Point n1 = translatePointForBounding(p1, 90 - rot, tolerance);
        Point n2 = translatePointForBounding(p1, 270 - rot, tolerance);

        Point n3 = translatePointForBounding(p2, 270 - rot, tolerance);
        Point n4 = translatePointForBounding(p2, 90 - rot, tolerance);

        int[] x = new int[] {n1.x, n2.x, n3.x, n4.x};
        int[] y = new int[] {n1.y, n2.y, n3.y, n4.y};
        return new Polygon(x, y, 4);
    }

    public Point translatePointForBounding(Point centre, double deg, double dist) {
        int x = (int) Math.round(centre.x + dist * Math.sin(Math.toRadians(deg)));
        int y = (int) Math.round(centre.y + dist * Math.cos(Math.toRadians(deg)));
        return new Point(x, y);
    }

    public GesturePointMap toPointMap() {
        GesturePointMap pointmap = new GesturePointMap();
        pointmap.addPoint(p1.x, p1.y);
        pointmap.addPoint(p2.x, p2.y);
        return pointmap;
    }

}
