package sourcecoded.quantum.api.gesture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This is used for the gestures included with the API by stock.
 * This is so you can do your own actions when the gesture is called.
 *
 * Implement this on any objects you wish to use as a callback
 */
public interface IGestureCallback {

    /**
     * Called when a gesture is completed
     * @param player The player that executed the gesture. This is really
     *               only for convenience, as it is called only on the Client-Side
     * @param world The world the gesture is in
     * @param pointMap A point map of the gesture. You can hook into this
     *                 for accessing each specific point traced by the player
     * @param itemstack The Itemstack the gesture was executed on
     */
    public void callbackGesture(EntityPlayer player, World world, GesturePointMap pointMap, ItemStack itemstack);

}
