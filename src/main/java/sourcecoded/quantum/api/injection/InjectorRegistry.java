package sourcecoded.quantum.api.injection;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InjectorRegistry {

    private static ArrayList<IInjectorRecipe> recipes = new ArrayList<IInjectorRecipe>();

    public static void addRecipe(IInjectorRecipe recipe) {
        recipes.add(recipe);
    }

    public static List<IInjectorRecipe> getRecipes() {
        return recipes;
    }

    public static boolean hasRecipeForInput(ItemStack item) {
        return getRecipeForInput(item) != null;
    }

    public static IInjectorRecipe getRecipeForInput(ItemStack item) {
        for (IInjectorRecipe recipe : recipes)
            if (matches(recipe.getInput(), item)) return recipe;
        return null;
    }

    public static boolean matches(ItemStack item1, ItemStack item2) {
        return item1.isItemEqual(item2);
    }

}
