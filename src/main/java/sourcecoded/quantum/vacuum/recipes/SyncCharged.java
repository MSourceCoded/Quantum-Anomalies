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

public class SyncCharged implements IVacuumRecipe {

    ItemStack[] ingredients;
    ItemStack[] catalysts;
    ItemStack[] outputs;

    public SyncCharged() {
    }

    @Override
    public List<ItemStack> getIngredients() {
        ingredients = new ItemStack[] {
                new ItemStack(QABlocks.SYNC.getBlock(), 1, 0),
                new ItemStack(Items.nether_star),
                new ItemStack(Items.ender_pearl, 4),
                new ItemStack(Items.ender_eye, 4),
                new ItemStack(Blocks.hopper),
                new ItemStack(Blocks.ender_chest),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 2, 1)
        };

        return Arrays.asList(ingredients);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        catalysts = new ItemStack[] {
                new ItemStack(Items.ender_pearl),
                new ItemStack(Items.ender_eye),
                new ItemStack(Blocks.chest),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem())
        };

        return Arrays.asList(catalysts);
    }

    @Override
    public List<ItemStack> getOutputs() {
        outputs = new ItemStack[] {
                new ItemStack(QABlocks.SYNC.getBlock(), 1, 1)
        };

        return Arrays.asList(outputs);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 200000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 6000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.APOCALYPTIC;
    }
}
