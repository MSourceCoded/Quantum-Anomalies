package sourcecoded.quantum.api.gesture.template;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGestureCallback;

/**
 * The Gesture class that will detect if a gesture is drawn in the shape of a Circle
 * or Ellipse.
 *
 * Do not extend this class, instead creative a new instance and register it as you
 * would any other gesture.
 *
 * @see sourcecoded.quantum.api.gesture.template.GestureTemplate
 */
public class GestureCircle extends GestureTemplate {

    public float xyRatio = 1.2F;
    public float minimumOffset = 8F;
    public float toleranceFactor = 1F;

    /**
     * Create a new Circle Gesture, using the default values
     * @param callback The interface to activate when the circle gesture is complete
     */
    public GestureCircle(IGestureCallback callback) {
        super(callback);
    }

    /**
     * Create a new Circle Gesture, using your own custom values
     * @param callback The interface to activate when the circle gesture is complete
     * @param xyMaxRatio The maximum ratio between the X and Y axis. By default this is set to 1.2F.
     *                   It is recommended not to set this to ~1.0F, as it is extremely difficult
     *                   for a player to draw a circle that accurate
     * @param minimumOffset The minimum offset from the centre to draw from
     * @param toleranceMultiplier Used for calculating the tolerance. maxX + maxY * toleranceMultiplier
     *                            is the calculation used. By default, this value is 1.0F
     */
    public GestureCircle(IGestureCallback callback, float xyMaxRatio, float minimumOffset, float toleranceMultiplier) {
        this(callback);
        this.xyRatio = xyMaxRatio;
        this.toleranceFactor = toleranceMultiplier;
        this.minimumOffset = minimumOffset;
    }

    @Override
    public boolean calculateGesture(EntityPlayer player, World world, ItemStack item, GesturePointMap tracer) {
        GesturePointMap toCompare = new GesturePointMap();
        if (Math.abs(tracer.maxX / tracer.maxY) > xyRatio) return false;        //Makes sure the circle is in the correct contortions

        float tol = (tracer.maxY + tracer.maxX) * toleranceFactor;              //Calculate the tolerance for the calculation

        float absMinX = Math.abs(tracer.minX);                                  //Make sure that the minimum is an absolute value
        if (absMinX < minimumOffset) return false;                              //Check that the minimum X value is at least the minimum offset

        toCompare.setTolerance(tol);                                            //Set the maximum tolerance

        float stepper = 1F / (float) tracer.getLength();                        //Used to generate the 'stepper' between each iteration of the circle

        for (int i = 0; i < tracer.getLength(); i++) {                          //Iterate between the length of the gesture
            float step = i * stepper;

            float maxX = tracer.maxX;                                           //Sets the size for the circle
            float maxY = tracer.maxY;

            float x = (float) (maxX * Math.cos(2 * Math.PI * step) - maxX);     //Calculate the current position of each point of the circle
            float y = (float) (maxY * Math.sin(2 * Math.PI * step));

            toCompare.addPoint(x, y);                                           //Add them to the comparator array
        }

        int result = toCompare.compareTo(tracer);                               //Get the result of the gesture. Does it match?
        if (result != 1) return false;                                          //Return false if they don't match

        this.doCallback(player, world, tracer, item);                           //Finally, callback the interface passed in the constructor
        return true;
    }
}
