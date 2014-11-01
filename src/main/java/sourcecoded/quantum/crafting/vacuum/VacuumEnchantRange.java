package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.event.crafting.VacuumCraftingEvent;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAEnchant;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VacuumEnchantRange implements IVacuumRecipe {

    int tier;

    public static ArrayList<VacuumEnchantRange> recipeList = new ArrayList<VacuumEnchantRange>();

    public static void registerAll() {
        for (int i = QAEnchant.RANGE.get().getMinLevel(); i <= QAEnchant.RANGE.get().getMaxLevel(); i++) {
            VacuumEnchantRange recipe = new VacuumEnchantRange(i);
            recipeList.add(recipe);
            VacuumRegistry.addRecipe(recipe);
        }
    }

    public VacuumEnchantRange(int tier) {
        this.tier = tier;
    }

    @Override
    public List<ItemStack> getIngredients() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(Items.book));

        for (int i = 0; i < tier; i++)
            stacks.add(new ItemStack(QAItems.RIFT_MAGNET.getItem()));

        return stacks;
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(Items.book));
        stacks.add(new ItemStack(Blocks.hopper));
        stacks.add(new ItemStack(Items.ender_pearl, tier));

        return stacks;
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] items = new ItemStack[] {
                new ItemStack(Items.enchanted_book)
        };

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(QAEnchant.RANGE.get().effectId, tier);

        EnchantmentHelper.setEnchantments(map, items[0]);

        return Arrays.asList(items);
    }

    @Override
    public int getVacuumEnergyStart() {
        return 20000 * tier;
    }

    @Override
    public int getVacuumEnergyPerItem() {
        return 5000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.DISMAL;
    }

    @Override
    public CraftingContext getContext() {
        return new CraftingContext().setOreDictionary(false).setRespectsNBT(false).setRespectsMeta(true);
    }
}
