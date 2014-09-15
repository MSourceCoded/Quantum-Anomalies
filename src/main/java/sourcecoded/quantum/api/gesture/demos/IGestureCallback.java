package sourcecoded.quantum.api.gesture.demos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;

public interface IGestureCallback {

    public void callbackGesture(EntityPlayer player, World world, GesturePointMap pointMap, ItemStack item);

}
