package sourcecoded.quantum.api.gesture;

import java.util.ArrayList;
import java.util.List;

public class GestureTrackMap {

    ArrayList<GestureDirection> directions = new ArrayList<GestureDirection>();

    public void addStep(GestureDirection dir) {
        directions.add(dir);
    }

    @SuppressWarnings("unchecked")
    public List<GestureDirection> getDirections() {
        return (List<GestureDirection>) directions.clone();
    }

    public int size() {
        return directions.size();
    }

}
