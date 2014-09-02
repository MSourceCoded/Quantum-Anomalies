package sourcecoded.quantum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemJournal extends ItemQuantum {

    public ItemJournal() {
        this.setTextureName("journal");
        this.setUnlocalizedName("itemJournal");
    }

    public String getItemStackDisplayName(ItemStack item) {
        return String.format(StatCollector.translateToLocal(getUnlocalizedName(item) + ".name"), EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.WHITE);
    }

    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean idk) {
        list.add(String.format(StatCollector.translateToLocal(getUnlocalizedName(item) + ".lore.0"), EnumChatFormatting.ITALIC));
    }

}
