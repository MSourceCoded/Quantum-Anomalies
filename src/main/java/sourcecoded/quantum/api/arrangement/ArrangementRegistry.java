package sourcecoded.quantum.api.arrangement;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * The registration class for Arrangement crafting.
 * This is where you register your Arrangement recipes. It is
 * recommended that you register at an FMLInitialization
 * event, but you can register whenever you need.
 *
 * Arrangement takes advantage of the ItemMatrix
 * object, which allows for the crafting to be used
 * in any Direction
 *
 * @see sourcecoded.quantum.api.arrangement.ItemMatrix
 *
 * @author SourceCoded
 */
public class ArrangementRegistry {

    /**
     * The ArrayList containing all the registered recipes
     */
    private static ArrayList<IArrangementRecipe> recipes = new ArrayList<IArrangementRecipe>();

    /**
     * Register a recipe in the registry. Again,
     * it is recommended that you call this at
     * an Initialization event, but not required.
     */
    public static void addRecipe(IArrangementRecipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Get a list of all the registered recipes.
     * If you plan on iterating through this list,
     * I recommend calling .clone(), as this returns
     * the List object itself.
     */
    public static List<IArrangementRecipe> getRecipes() {
        return recipes;
    }

    /**
     * Returns the Arrangement Recipe for the given ItemMatrix.
     * This will return null if there is no recipe found
     */
    public static IArrangementRecipe getRecipe(ItemMatrix grid) {
        for (IArrangementRecipe recipe : recipes)
            if (recipe.matches(grid)) return recipe;

        return null;
    }

    /**
     * Returns the recipe for the recipe
     */
    public static IArrangementRecipe getRecipeForOutput(ItemStack output) {
        for (IArrangementRecipe recipe : recipes)
            if (OreDictionary.itemMatches(output, recipe.getOutput(), false))
                return recipe;

        return null;
    }

}
