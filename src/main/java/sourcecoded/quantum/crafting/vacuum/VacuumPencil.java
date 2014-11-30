package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class VacuumPencil implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] list = new ItemStack[] {
                new ItemStack(QAItems.INJECTED_STICK.getItem(), 4, 1),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1),

                new ItemStack(Items.dye, 4),
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1),
            new ItemStack(Items.dye),
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.PENCIL.getItem())
        };

        return Arrays.asList(list);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 100000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 3000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.EXTREME;
    }

    @Override
    public CraftingContext getContext() {
        return new CraftingContext().setOreDictionary(true).setRespectsMeta(true).setRespectsNBT(false);
    }
}
