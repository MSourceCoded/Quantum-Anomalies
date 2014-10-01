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

public class VacuumManipulation implements IVacuumRecipe {

    ItemStack[] ingredients;
    ItemStack[] catalysts;
    ItemStack[] outputs;

    public VacuumManipulation() {
    }

    @Override
    public List<ItemStack> getIngredients() {
        ingredients = new ItemStack[] {
                new ItemStack(QABlocks.INJECTED_STONE.getBlock(), 4),
                new ItemStack(Items.water_bucket),
                new ItemStack(Blocks.netherrack),
                new ItemStack(Blocks.grass),
                new ItemStack(Items.dye, 1, 15),
                new ItemStack(Items.redstone, 2),
                new ItemStack(Blocks.ice),
                new ItemStack(Blocks.snow)
        };

        return Arrays.asList(ingredients);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        catalysts = new ItemStack[] {
                new ItemStack(Blocks.dispenser),
                new ItemStack(Blocks.grass),
                new ItemStack(Blocks.stone),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1)
        };

        return Arrays.asList(catalysts);
    }

    @Override
    public List<ItemStack> getOutputs() {
        outputs = new ItemStack[] {
                new ItemStack(QABlocks.MANIPULATION_STANDARD.getBlock())
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
        return Instability.DISMAL;
    }

}
