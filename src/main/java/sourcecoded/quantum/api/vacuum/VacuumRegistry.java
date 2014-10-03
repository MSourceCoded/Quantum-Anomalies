package sourcecoded.quantum.api.vacuum;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * The registration class for Vacuum Catalyst crafting.
 * This is where you register your Vacuum recipes. It is
 * recommended that you register at an FMLInitialization
 * event, but you can register whenever you need.
 *
 * @author SourceCoded
 */
public class VacuumRegistry {

    /**
     * The ArrayList containing all the registered recipes
     */
    private static ArrayList<IVacuumRecipe> recipes = new ArrayList<IVacuumRecipe>();

    /**
     * Register a recipe in the registry. Again,
     * it is recommended that you call this at
     * an Initialization event, but not required.
     */
    public static void addRecipe(IVacuumRecipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Get a list of all the registered recipes.
     * If you plan on iterating through this list,
     * I recommend calling .clone(), as this returns
     * the List object itself.
     */
    public static List<IVacuumRecipe> getRecipes() {
        return recipes;
    }

    /**
     * Returns if this registry has a recipe for the
     * specified catalysts. This is just a
     * null check on getRecipeForCatalyst
     */
    public static boolean hasRecipeForCatalyst(List<ItemStack> catalysts) {
        return getRecipeForCatalyst(catalysts) != null;
    }

    /**
     * Returns the Vacuum Recipe for the given catalysts.
     * This will return null if there is no recipe found
     */
    public static IVacuumRecipe getRecipeForCatalyst(List<ItemStack> catalysts) {
        for (IVacuumRecipe recipe : getRecipes()) {
            if (matches(recipe.getCatalysts(), catalysts))
                return recipe;
        }
        return null;
    }

    /**
     * Returns true if both ItemStacks match. This does
     * not yet take into account NBT tags, only Item and
     * Meta
     */
    public static boolean matches(ItemStack item1, ItemStack item2) {
        //return item1.isItemEqual(item2);
        return OreDictionary.itemMatches(item1, item2, false);

    }

    /**
     * Iterates two lists into the matches() method above.
     */
    public static boolean matches(List<ItemStack> l1, List<ItemStack> l2) {
        if (l1.size() != l2.size()) return false;

        for (int i = 0; i < l1.size(); i++) {
            if (!matches(l1.get(i), l2.get(i)))
                return false;
        }

        return true;
    }

}
