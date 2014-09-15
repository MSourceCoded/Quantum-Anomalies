package sourcecoded.quantum.api.gesture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.debugging.GUI;

import java.awt.*;

public abstract class AbstractGesture {

    public abstract GestureSegment[] getSegments(GesturePointMap tracer);

    public abstract void onCompletion(EntityPlayer player, World world, ItemStack itemStack, GesturePointMap tracer);

    public boolean attemptGesture(EntityPlayer player, World world, ItemStack itemstack, GesturePointMap tracer) {
        final GestureSegment[] segments = getSegments(tracer);

        final GesturePointMap tracer2 = tracer;

        int progress = 0;

        new Thread() {
            public void run() {
                this.setName("Gesture Thread");
                GUI tracer = new GUI(tracer2);
                tracer.setVisible(true);
                for (GestureSegment seg : segments) {
                    tracer.addPolygon(seg.getBounding());
                }
            }
        }.start();

        for (int i = 0; i < tracer.length(); i++) {
            Point point = tracer.getPoint(i);

            //I'll fix all this later, I've got other things to do

//            for (GestureSegment segment : segments) {
//                if (segment.isPointInBounds(point)) {
//                    segment.complete();
//                    break;
//                }
//            }

            loop:
            for (int j = progress; j < segments.length; j++) {
                GestureSegment segment = segments[j];
                if (segment.isPointInBounds(point)) {
                    segment.complete();
                    progress++;
                    break loop;
                }
            }
        }

        boolean flag = true;
        for (GestureSegment segment : segments)
            if (!segment.isCompleted())
                flag = false;

        if (flag) {
            onCompletion(player, world, itemstack, tracer);
            return true;
        } else
            return false;
    }

}
