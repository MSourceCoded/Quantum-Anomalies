package sourcecoded.quantum.api.gesture.demos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.AbstractGesture;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.GestureSegment;

public abstract class GestureTemplate extends AbstractGesture {

    IGestureCallback callback;

    public GestureTemplate(IGestureCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCompletion(EntityPlayer player, World world, ItemStack itemStack, GesturePointMap tracer) {
        callback.callbackGesture(player, world, tracer, itemStack);
    }
}
