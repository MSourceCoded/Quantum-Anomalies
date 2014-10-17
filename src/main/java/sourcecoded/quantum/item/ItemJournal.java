package sourcecoded.quantum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.translation.LocalizationUtils;

public class ItemJournal extends ItemQuantum {

    public ItemJournal() {
        this.setTextureName("journal");
        this.setUnlocalizedName("itemJournal");
    }

    public String getItemStackDisplayName(ItemStack item) {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(item) + ".name", "{c:PURPLE}Anomolical Journal{c:WHITE}");
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(QuantumAnomalies.instance, 0, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
}
