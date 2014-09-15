package sourcecoded.quantum.debugging;

import sourcecoded.quantum.api.gesture.GesturePointMap;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    //Used for debugging gestures

    Lines panel;

    public GUI(GesturePointMap map) {
        panel = new Lines(map);

        add(panel);

        setSize(350, 350);
        setLocationRelativeTo(null);
    }

    public void addPolygon(Polygon poly) {
        panel.addPoly(poly);
    }

}
