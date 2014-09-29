package sourcecoded.quantum.api.vacuum;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * An interface for Vacuum Catalyst Crafting recipes.
 * This can be implemented wherever you like, as long as it
 * is registered in the VacuumRegistry
 *
 * @see sourcecoded.quantum.api.vacuum.VacuumRegistry
 *
 * When implementing this interface, be sure to generate
 * a new List of ItemStacks each time the methods are called.
 * Vacuum Crafting doesn't copy the ItemStacks, so they must be
 * constructed in the methods below.
 *
 * @author SourceCoded
 */
public interface IVacuumRecipe {

    /**
     * Get a list of ItemStacks for use in the Ingredients
     * container of the Vacuum Chamber. These Items are
     * consumed in the crafting.
     */
    public List<ItemStack> getIngredients();

    /**
     * Get a list of ItemStacks for use in the Catalysts
     * container of the Vacuum Chamber. These Items
     * are not consumed, but instead act as a unique
     * identifier for the recipe to be crafted.
     *
     * Ensure your Catalyst pattern is not already used
     * by calling hasRecipeForCatalyst in the
     * Vacuum Registry, as conflicts are resolved
     * by the first one to register is the one that
     * is identified by the Crafting Algorithm.
     *
     * @see sourcecoded.quantum.api.vacuum.VacuumRegistry#hasRecipeForCatalyst(java.util.List)
     */
    public List<ItemStack> getCatalysts();

    /**
     * Get a list of ItemStacks for use in the Output
     * container of the Vacuum Chamber. This is the
     * result of the Crafting operation.
     */
    public List<ItemStack> getOutputs();

    /**
     * Get the amount of energy, as an int, to consume
     * when the crafting begins. This is the 'start-up'
     * cost of the crafting operation, and is often quite
     * high.
     */
    public int getVacuumEnergyStart();

    /**
     * Get the amount of energy, as an int, to consume
     * per ingredient. This is taken from the node
     * each time an item is taken from the Ingredients
     * container.
     */
    public int getVacuumEnergyPerItem();

    /**
     * Get the Enumeration of Instability for this recipe.
     * Instability is only calculated if the recipe goes
     * wrong (multiblock broken, ingredients missing, out
     * of energy, etc).
     *
     * @see sourcecoded.quantum.api.vacuum.Instability
     */
    public Instability getInstabilityLevel();
}
