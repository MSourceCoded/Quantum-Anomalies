package sourcecoded.quantum.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class ItemInjectedStick extends ItemQuantum implements IInjectorRecipe, IVacuumRecipe {

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
    public List<ItemStack> getIngredients() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(this, 1, 0),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 2, 1)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.stick),
                new ItemStack(QABlocks.INJECTED_STONE.getBlock())
        };
        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(this, 1, 1)
        };
        return Arrays.asList(stacks);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 10000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 500;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.SMALL;
    }
}
