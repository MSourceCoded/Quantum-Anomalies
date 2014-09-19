package sourcecoded.quantum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.util.List;

public class ItemJournal extends ItemQuantum {

    public ItemJournal() {
        this.setTextureName("journal");
        this.setUnlocalizedName("itemJournal");
    }

    public String getItemStackDisplayName(ItemStack item) {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(item) + ".name", "{c:PURPLE}Anomolical Journal{c:WHITE}");
    }

    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean idk) {
        list.add(LocalizationUtils.translateLocalWithColours(getUnlocalizedName(item) + ".lore.0", "{c:ITALIC}[ah-nom-ol-ic-al]"));
    }

}
