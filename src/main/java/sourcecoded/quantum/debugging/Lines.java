package sourcecoded.quantum.debugging;

import sourcecoded.quantum.api.gesture.GesturePointMap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Lines extends JPanel {

    public GesturePointMap points;

    public ArrayList<Polygon> polygons = null;

    public Lines(GesturePointMap map) {
        points = map;
    }

    public void addPoly(Polygon poly) {
        if (polygons == null) polygons = new ArrayList<Polygon>();
        polygons.add(poly);

        this.repaint();
    }

    public void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;

        for (int i = 1; i < points.length(); i++) {
            Point p1 = points.getPoint(i);
            Point p2 = points.getPoint(i - 1);

            g.drawLine(p1.x * 3 + 100, p1.y * 3 + 100, p2.x * 3 + 100, p2.y * 3 + 100);
        }

        g.setColor(Color.RED);

        if (polygons != null)
            for (Polygon poly : polygons) {
                for (int i = 1; i <= poly.npoints; i++) {
                    int x1 = poly.xpoints[i - 1];
                    int y1 = poly.ypoints[i - 1];

                    int x2, y2;

                    if (i != poly.npoints) {
                        x2 = poly.xpoints[i];
                        y2 = poly.ypoints[i];
                    } else {
                        x2 = poly.xpoints[0];
                        y2 = poly.ypoints[0];
                    }

                    g.drawLine(x1 * 3 + 100, y1 * 3 + 100, x2 * 3 + 100, y2 * 3 + 100);
                }
            }

        g.setColor(Color.BLACK);
    }

}
