package sourcecoded.quantum.api.arrangement;

import java.util.ArrayList;
import java.util.List;

public class ArrangementRegistry {

    private static ArrayList<IArrangementRecipe> recipes = new ArrayList<IArrangementRecipe>();

    public static void addRecipe(IArrangementRecipe recipe) {
        recipes.add(recipe);
    }

    public static List<IArrangementRecipe> getRecipes() {
        return recipes;
    }

    public static IArrangementRecipe getRecipe(ItemMatrix grid) {
        for (IArrangementRecipe recipe : recipes)
            if (recipe.matches(grid)) return recipe;

        return null;
    }

}
