package sourcecoded.quantum.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.crafting.vacuum.VacuumToolRod;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class ItemInjectedStick extends ItemQuantum implements IInjectorRecipe, ICraftableItem {

    public ItemInjectedStick() {
        this.setTextureName("infusedStick");
        this.setUnlocalizedName("itemInjectedStick");

        this.setHasSubtypes(true);
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }

    public boolean hasEffect(ItemStack stack, int meta) {
        return stack.getItemDamage() == 1;
    }

    @Override
    public int getEnergyRequired() {
        return InjectionConstants.INJECTION_STANDARD_ITEM;
    }

    @Override
    public byte getTier() {
        return 1;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Items.stick, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }


    @Override
    public IRecipe[] getRecipes(Item item) {
        VacuumRegistry.addRecipe(new VacuumToolRod());
        return new IRecipe[0];
    }
}
