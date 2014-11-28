package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class VacuumToolRod implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] list = new ItemStack[] {
                new ItemStack(QAItems.INJECTED_STICK.getItem(), 4),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 4, 1),

                new ItemStack(Items.blaze_rod, 16)
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.INJECTED_STICK.getItem()),
            new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem()),
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.INJECTED_STICK.getItem(), 2, 1)
        };

        return Arrays.asList(list);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 500000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 10000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.CATACLYSMIC_SWITCH;
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }
}
