package sourcecoded.quantum.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtils {

    public static void setVelocityClient(double x, double y, double z) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        player.motionX += x;
        player.motionY += y;
        player.motionZ += z;
    }

    public static int crawlInventoryForItem(EntityPlayer player, Item item) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == item)
                return i;
        }
        return -1;
    }

}
