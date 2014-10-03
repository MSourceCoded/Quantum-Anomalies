package sourcecoded.quantum.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerUtils {

    public static void setVelocityClient(double x, double y, double z) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.motionX += x;
        player.motionY += y;
        player.motionZ += z;
    }

}
