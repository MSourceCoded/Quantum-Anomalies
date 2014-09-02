package sourcecoded.quantum.api.gesture.template;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.gesture.IGestureCallback;

/**
 * A stock Gesture class that automatically handles the callback for you.
 * Override the constructor and call the "doCallback" method if the
 * calculateGesture function in IGesture is successful.
 *
 * @see sourcecoded.quantum.api.gesture.IGestureCallback
 * @see sourcecoded.quantum.api.gesture.IGesture
 */
public abstract class GestureTemplate implements IGesture {

    public IGestureCallback callback;

    /**
     * The default constructor, adds the callback to the object
     * @param callback The callback to use
     */
    public GestureTemplate(IGestureCallback callback) {
        this.callback = callback;
    }

    /**
     * Handles the callback of the object. Call this method in the
     * calculateGesture method if it is successful
     * @param player The player that executed the gesture
     * @param world The world of the player
     * @param pointMap The point map of the gesture
     */
    public void doCallback(EntityPlayer player, World world, GesturePointMap pointMap, ItemStack item) {
        callback.callbackGesture(player, world, pointMap, item);
    }

}
