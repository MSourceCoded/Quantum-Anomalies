package sourcecoded.quantum.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.api.discovery.DiscoveryRegistry;
import sourcecoded.quantum.api.translation.LocalizationUtils;

public class ItemJournalCheatSheet extends ItemQuantum {

    public ItemJournalCheatSheet() {
        this.setTextureName("journal");
        this.setUnlocalizedName("itemJournalCheat");
    }

    public String getItemStackDisplayName(ItemStack item) {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(item) + ".name", "{c:PURPLE}Anomolical Journal (Cheaty Cheat Sheet){c:WHITE}");
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            for (String item : DiscoveryRegistry.getItemKeyList())
                DiscoveryManager.unlockItem(item, player, true);
        }
        return stack;
    }
}
