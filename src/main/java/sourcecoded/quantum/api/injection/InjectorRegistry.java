package sourcecoded.quantum.api.injection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
/**
 * The registration class for Injection crafting.
 * This is where you register your Injection recipes. It is
 * recommended that you register at an FMLInitialization
 * event, but you can register whenever you need.
 *
 * @author SourceCoded
 */
public class InjectorRegistry {

    /**
     * The ArrayList containing all the registered recipes
     */
    private static ArrayList<IInjectorRecipe> recipes = new ArrayList<IInjectorRecipe>();

    /**
     * Register a recipe in the registry. Again,
     * it is recommended that you call this at
     * an Initialization event, but not required.
     */
    public static void addRecipe(IInjectorRecipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Get a list of all the registered recipes.
     * If you plan on iterating through this list,
     * I recommend calling .clone(), as this returns
     * the List object itself.
     */
    public static List<IInjectorRecipe> getRecipes() {
        return recipes;
    }

    /**
     * Returns if this registry has a recipe for the
     * specified itemstack. This is just a
     * null check on getRecipeForCatalyst
     */
    public static boolean hasRecipeForInput(ItemStack item) {
        return getRecipeForInput(item) != null;
    }

    /**
     * Returns the Injection Recipe for the given itemstack.
     * This will return null if there is no recipe found
     */
    public static IInjectorRecipe getRecipeForInput(ItemStack item) {
        for (IInjectorRecipe recipe : recipes)
            if (recipe.getContext().matches(recipe.getInput(), item)) return recipe;
        return null;
    }

    public static IInjectorRecipe getRecipeForOutput(ItemStack output) {
        for (IInjectorRecipe recipe : recipes)
            if (matches(recipe.getOutput(), output)) return recipe;
        return null;
    }

    /**
     * Returns true if both ItemStacks match. This does
     * not yet take into account NBT tags, only Item and
     * Meta
     */
    public static boolean matches(ItemStack item1, ItemStack item2) {
        return OreDictionary.itemMatches(item1, item2, false);
    }

}
