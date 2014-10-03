package sourcecoded.quantum.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtils {

    public static void checkCompound(ItemStack item) {
        if (item.stackTagCompound == null) item.stackTagCompound = new NBTTagCompound();
    }

}
