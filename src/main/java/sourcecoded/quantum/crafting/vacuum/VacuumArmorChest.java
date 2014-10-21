package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class VacuumArmorChest implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] list = new ItemStack[] {
                new ItemStack(QAItems.ENTROPIC_STAR.getItem(), 8),
                new ItemStack(Items.diamond_chestplate),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 4, 1),
                new ItemStack(Items.arrow, 32),
                new ItemStack(Items.snowball, 16),
                new ItemStack(Items.ender_pearl, 4),
                new ItemStack(Items.ender_eye, 2)
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QABlocks.INJECTED_STONE.getBlock()),
            new ItemStack(Items.diamond_chestplate)
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.RIFT_CHEST.getItem())
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
