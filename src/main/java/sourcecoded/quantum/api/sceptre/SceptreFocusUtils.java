package sourcecoded.quantum.api.sceptre;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SceptreFocusUtils {

    public static NBTTagCompound getAllocatedNBT(ISceptreFocus focus, ItemStack stack) {
        NBTTagCompound stackTag = stack.stackTagCompound;
        if (!stackTag.hasKey("focusData")) stackTag.setTag("focusData", new NBTTagCompound());
        NBTTagCompound focusData = stackTag.getCompoundTag("focusData");
        if (!focusData.hasKey(focus.getFocusIdentifier())) focusData.setTag(focus.getFocusIdentifier(), new NBTTagCompound());

        return focusData.getCompoundTag(focus.getFocusIdentifier());
    }

}
