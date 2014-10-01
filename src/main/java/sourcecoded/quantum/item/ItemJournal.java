package sourcecoded.quantum.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.translation.LocalizationUtils;

public class ItemJournal extends ItemQuantum {

    public ItemJournal() {
        this.setTextureName("journal");
        this.setUnlocalizedName("itemJournal");
    }

    public String getItemStackDisplayName(ItemStack item) {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(item) + ".name", "{c:PURPLE}Anomolical Journal{c:WHITE}");
    }
}
