package sourcecoded.quantum.api.arrangement;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.CraftingContext;

/**
 * An interface for use with custom ArrangementRecipes.
 * This is registered in the ArrangementRegistry
 *
 * @see sourcecoded.quantum.api.arrangement.ArrangementRegistry
 *
 * @author SourceCoded
 */
public interface IArrangementRecipe {

    /**
     * Does this recipe match the given
     * ItemMatrix grid
     */
    public boolean matches(ItemMatrix grid);

    /**
     * Get the Output ItemStack for the
     * recipe. It is recommended
     * to use .copy() here
     */
    public ItemStack getOutput();

    /**
     * Get the ItemMatrix for the recipe
     */
    public ItemMatrix getMatrix();

    /**
     * Get the crafting context for this recipe. This denotes
     * whether or not to respect OreDictionary, MetaData
     * or NBT.
     */
    public CraftingContext getContext();

}
