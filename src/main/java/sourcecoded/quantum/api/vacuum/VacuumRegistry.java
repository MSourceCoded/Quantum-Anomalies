package sourcecoded.quantum.api.vacuum;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VacuumRegistry {

    private static ArrayList<IVacuumRecipe> recipes = new ArrayList<IVacuumRecipe>();

    public static void addRecipe(IVacuumRecipe recipe) {
        recipes.add(recipe);
    }

    public static List<IVacuumRecipe> getRecipes() {
        return recipes;
    }

    public static boolean hasRecipeForCatalyst(List<ItemStack> catalysts) {
        return getRecipeForCatalyst(catalysts) != null;
    }

    public static IVacuumRecipe getRecipeForCatalyst(List<ItemStack> catalysts) {
        for (IVacuumRecipe recipe : getRecipes()) {
            if (matches(recipe.getCatalysts(), catalysts))
                return recipe;
        }
        return null;
    }

    public static boolean matches(ItemStack item1, ItemStack item2) {
        return item1.isItemEqual(item2);
    }

    public static boolean matches(List<ItemStack> l1, List<ItemStack> l2) {
        if (l1.size() != l2.size()) return false;

        for (int i = 0; i < l1.size(); i++) {
            if (!matches(l1.get(i), l2.get(i)))
                return false;
        }

        return true;
    }

}
