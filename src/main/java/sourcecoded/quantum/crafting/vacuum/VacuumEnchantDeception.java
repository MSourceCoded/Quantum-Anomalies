package sourcecoded.quantum.crafting.vacuum;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.registry.QAEnchant;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VacuumEnchantDeception implements IVacuumRecipe {

    int tier;

    public static ArrayList<VacuumEnchantDeception> recipeList = new ArrayList<VacuumEnchantDeception>();

    public static void registerAll() {
        for (int i = QAEnchant.DECEPTION.get().getMinLevel(); i <= QAEnchant.DECEPTION.get().getMaxLevel(); i++) {
            VacuumEnchantDeception recipe = new VacuumEnchantDeception(i);
            recipeList.add(recipe);
            VacuumRegistry.addRecipe(recipe);
        }
    }

    public VacuumEnchantDeception(int tier) {
        this.tier = tier;
    }

    @Override
    public List<ItemStack> getIngredients() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(Items.book));
        stacks.add(new ItemStack(Items.ender_eye));
        stacks.add(new ItemStack(QAItems.ENTROPIC_STAR.getItem(), tier));

        for (int i = 0; i < tier; i++)
            stacks.add(new ItemStack(Items.potionitem, 1, Potion.invisibility.getId()));

        return stacks;
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(new ItemStack(Items.book));
        stacks.add(new ItemStack(Items.ender_eye, tier));

        return stacks;
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] items = new ItemStack[] {
                new ItemStack(Items.enchanted_book)
        };

        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(QAEnchant.DECEPTION.get().effectId, tier);

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
