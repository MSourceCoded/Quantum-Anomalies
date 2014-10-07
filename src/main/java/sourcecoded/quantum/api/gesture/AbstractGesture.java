package sourcecoded.quantum.api.gesture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGesture {

    public abstract GestureTrackMap getGestureMap();

    public abstract void onGestureCompleted(EntityPlayer player, World world, ItemStack itemstack, GestureTrackMap compareMap);

    public boolean attemptGesture(EntityPlayer player, World world, ItemStack itemstack, GestureTrackMap compare) {
        GestureTrackMap thisMap = getGestureMap();
        if (compare.size() != thisMap.size()) return false;

        List<GestureDirection> dirComp = compare.getDirections();
        List<GestureDirection> dirThis = thisMap.getDirections();

        if (dirComp.equals(dirThis)) {
            onGestureCompleted(player, world, itemstack, compare);
            return true;
        }

        return false;
    }

}
