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

public class VacuumEntropicStar implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.nether_star, 2),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 2, 1),
                new ItemStack(Blocks.obsidian, 4)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.nether_star),
                new ItemStack(QABlocks.INJECTED_STONE.getBlock()),
                new ItemStack(Blocks.glass)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(QAItems.ENTROPIC_STAR.getItem(), 1)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 200000;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 5000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.EXTREME;
    }
}
