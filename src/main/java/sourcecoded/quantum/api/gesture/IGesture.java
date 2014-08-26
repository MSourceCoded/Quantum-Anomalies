package sourcecoded.quantum.api.gesture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface that depicts a class as a gesture. Gestures are used
 * in the Sceptres and any items that require the players' view
 * to move in a specific motion to allow an action to take place.
 *
 * Gestures are registered in Quantum Foci classes. Each mod that
 * uses gestures are required to have at least 1 Quantum Focus. This
 * is to make sure gestures don't conflict with each other, as they
 * are separated into Foci groups.
 */
public interface IGesture {

    /**
     * Called after a gesture has been drawn and is ready to be calculated.
     * It should be noticed that this only occurs CLIENT SIDE!
     * @param player The player that drew the gesture
     * @param itemstack The itemstack that is currently doing the gesture
     * @param world The world of the player
     * @param tracer The GesturePointMap of the gesture that was drawn. Compare
     *               this to your own
     * @return True if the gestures match, false if they don't
     */
    public boolean calculateGesture(EntityPlayer player, World world, ItemStack itemstack, GesturePointMap tracer);
}