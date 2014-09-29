package sourcecoded.quantum.api.sceptre;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A utility class for the Sceptre and Sceptre
 * Foci.
 *
 * @author SourceCoded
 */
public class SceptreFocusUtils {

    /**
     * Get the allocated NBTTagCompound for the given
     * focus. This is used so multiple foci
     * can use the ItemStack's NBT without
     * conflicting with each other.
     *
     * @see sourcecoded.quantum.api.sceptre.ISceptreFocus
     */
    public static NBTTagCompound getAllocatedNBT(ISceptreFocus focus, ItemStack stack) {
        NBTTagCompound stackTag = stack.stackTagCompound;
        if (!stackTag.hasKey("focusData")) stackTag.setTag("focusData", new NBTTagCompound());
        NBTTagCompound focusData = stackTag.getCompoundTag("focusData");
        if (!focusData.hasKey(focus.getFocusIdentifier())) focusData.setTag(focus.getFocusIdentifier(), new NBTTagCompound());

        return focusData.getCompoundTag(focus.getFocusIdentifier());
    }

}
