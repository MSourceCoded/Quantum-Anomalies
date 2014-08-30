package sourcecoded.quantum.api.sceptre;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SceptreFocusRegistry {

    private static ArrayList<ISceptreFocus> foci = new ArrayList<ISceptreFocus>();

    /**
     * Register a focus in the registry
     */
    public static void registerFocus(ISceptreFocus focus) {
        foci.add(focus);
    }

    /**
     * Get a focus by it's identifier. Note you will
     * have to check "canBeUsed()" by yourself, as
     * this method does not account for that
     */
    public static ISceptreFocus getFocus(String identifier) {
        for (ISceptreFocus aFoci : foci) if (aFoci.getFocusIdentifier().equals(identifier)) return aFoci;

        return null;
    }

    /**
     * Get the next focus in the list
     */
    public static ISceptreFocus getNextFocus(ISceptreFocus focus, EntityPlayer player, ItemStack itemstack) {
        if (foci.size() == 0) return null;
        int index = foci.indexOf(focus);
        if (index == -1 && focus != null) return null;

        index++;

        if (index == foci.size()) return null;
        ISceptreFocus next = foci.get(index);
        if (!next.canBeUsed(player, itemstack))
            return getNextFocus(next, player, itemstack);
        else
            return next;

    }

}
