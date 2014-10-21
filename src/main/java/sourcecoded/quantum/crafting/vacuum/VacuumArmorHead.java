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

public class VacuumArmorHead implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] list = new ItemStack[] {
                new ItemStack(QAItems.ENTROPIC_STAR.getItem(), 5),
                new ItemStack(Items.diamond_helmet),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 4, 1),
                new ItemStack(Items.water_bucket, 2),
                new ItemStack(Items.ender_pearl, 4),
                new ItemStack(Items.ender_eye, 2)
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QABlocks.INJECTED_STONE.getBlock()),
            new ItemStack(Items.diamond_helmet)
        };

        return Arrays.asList(list);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] list = new ItemStack[] {
            new ItemStack(QAItems.RIFT_HELM.getItem())
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
