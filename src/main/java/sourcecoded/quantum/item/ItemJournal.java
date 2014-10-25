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
import sourcecoded.quantum.api.translation.LocalizationUtils;

public class ItemJournal extends ItemQuantum implements ICraftableItem{

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

    @Override
    public IRecipe[] getRecipes(Item item) {
        GameRegistry.addShapelessRecipe(new ItemStack(this), Items.writable_book, Items.redstone, Blocks.obsidian);
        return new IRecipe[0];
    }
}
