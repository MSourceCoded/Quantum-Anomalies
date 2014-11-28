package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class VacuumPlayer implements IVacuumRecipe {

    ItemStack[] ingredients;
    ItemStack[] catalysts;
    ItemStack[] outputs;

    public VacuumPlayer() {
    }

    @Override
    public List<ItemStack> getIngredients() {
        ingredients = new ItemStack[] {
                new ItemStack(QABlocks.INJECTED_STONE.getBlock(), 4, 1),
                new ItemStack(Items.ender_eye, 4),
                new ItemStack(Blocks.ender_chest),
                new ItemStack(Items.experience_bottle, 2),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1)
        };

        return Arrays.asList(ingredients);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        catalysts = new ItemStack[] {
                new ItemStack(Blocks.chest),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1)
        };

        return Arrays.asList(catalysts);
    }

    @Override
    public List<ItemStack> getOutputs() {
        outputs = new ItemStack[] {
                new ItemStack(QABlocks.PLAYER.getBlock())
        };

        return Arrays.asList(outputs);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 25000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 1000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.CRITICAL;
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }

}
