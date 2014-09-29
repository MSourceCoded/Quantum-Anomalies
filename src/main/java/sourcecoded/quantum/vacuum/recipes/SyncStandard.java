package sourcecoded.quantum.vacuum.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class SyncStandard implements IVacuumRecipe {

    ItemStack[] ingredients;
    ItemStack[] catalysts;
    ItemStack[] outputs;

    public SyncStandard() {
    }

    @Override
    public List<ItemStack> getIngredients() {
        ingredients = new ItemStack[] {
                new ItemStack(Items.ender_eye, 4),
                new ItemStack(Items.ender_pearl, 2),
                new ItemStack(Blocks.redstone_block),
                new ItemStack(Items.gold_ingot, 2),
                new ItemStack(QABlocks.MANIPULATION_STANDARD.getBlock(), 1),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1)
        };

        return Arrays.asList(ingredients);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        catalysts = new ItemStack[] {
                new ItemStack(Items.ender_pearl),
                new ItemStack(Items.ender_eye),
                new ItemStack(Items.nether_star),
                new ItemStack(Items.ender_eye),
                new ItemStack(Items.ender_pearl),
        };

        return Arrays.asList(catalysts);
    }

    @Override
    public List<ItemStack> getOutputs() {
        outputs = new ItemStack[] {
                new ItemStack(QABlocks.SYNC.getBlock(), 4)
        };

        return Arrays.asList(outputs);
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
}
