package sourcecoded.quantum.vacuum.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.registry.QAItems;

import java.util.Arrays;
import java.util.List;

public class VacuumMagnet implements IVacuumRecipe {

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.iron_ingot, 8),
                new ItemStack(Items.ender_pearl, 16),
                new ItemStack(Items.gold_ingot, 4),
                new ItemStack(QAItems.ENTROPIC_STAR.getItem()),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 2, 1),
                new ItemStack(QAItems.INJECTED_STRING.getItem(), 3)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.ender_pearl),
                new ItemStack(Items.fishing_rod, 1, OreDictionary.WILDCARD_VALUE)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(QAItems.RIFT_MAGNET.getItem())
        };

        return Arrays.asList(stacks);
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
        return Instability.CATACLYSMIC_SWITCH;
    }
}
