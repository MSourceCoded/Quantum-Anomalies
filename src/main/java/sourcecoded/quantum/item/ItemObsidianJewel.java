package sourcecoded.quantum.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.entity.EntityItemJewel;
import sourcecoded.quantum.registry.QAItems;

public class ItemObsidianJewel extends ItemQuantum implements ICraftableItem, IInjectorRecipe {

    public ItemObsidianJewel() {
        this.setTextureName("obsidianJewel");
        this.setUnlocalizedName("itemObsidianJewel");
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(8);
    }

    public boolean hasCustomEntity(ItemStack stack) {
        return stack.getItemDamage() == 1;
    }

    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityItemJewel(world, location, itemstack);
    }

    @Override
    public IRecipe[] getRecipes(Item item) {
        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem()), "ogo", "gdg", "ogo", 'o', Blocks.obsidian, 'g', Blocks.glass, 'd', Items.diamond));
        return new IRecipe[0];
    }

    public boolean hasEffect(ItemStack stack) {
        return getDamage(stack) == 1;
    }

    @Override
    public int getEnergyRequired() {
        return 100000;
    }

    @Override
    public byte getTier() {
        return 3;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 1);
    }
}
