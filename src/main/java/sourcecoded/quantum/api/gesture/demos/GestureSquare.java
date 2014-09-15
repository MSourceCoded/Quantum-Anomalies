package sourcecoded.quantum.api.gesture.demos;

import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.GestureSegment;

import java.awt.*;

public class GestureSquare extends GestureTemplate {

    public GestureSquare(IGestureCallback callback) {
        super(callback);
    }

    @Override
    public GestureSegment[] getSegments(GesturePointMap tracer) {
        int Mx = (int) tracer.maxX;
        int mx = (int) tracer.minX;
        int My = (int) tracer.maxY;
        int my = (int) tracer.minY;

        int tol = 5;

        GestureSegment seg1 = new GestureSegment(new Point(mx, my), new Point(mx, My), tol);
        GestureSegment seg2 = new GestureSegment(new Point(mx, My), new Point(Mx, My), tol);
        GestureSegment seg3 = new GestureSegment(new Point(Mx, My), new Point(Mx, my), tol);
        GestureSegment seg4 = new GestureSegment(new Point(Mx, my), new Point(mx, my), tol);

        return new GestureSegment[]{seg1, seg2, seg3, seg4};
    }
}
