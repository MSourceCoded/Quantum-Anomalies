package sourcecoded.quantum.api.injection;

import net.minecraft.item.ItemStack;

/**
 * An interface for use with Quantum Injection.
 * This can be implemented anywhere, as long as
 * it is registered in the InjectorRegistry
 *
 * @see sourcecoded.quantum.api.injection.InjectorRegistry
 *
 * @author SourceCoded
 */
public interface IInjectorRecipe {

    /**
     * Get the amount of energy required for the injection
     * to complete
     */
    public int getEnergyRequired();

    /**
     * Get the Tier of the injection.
     * Tiers range from 0-3
     */
    public byte getTier();

    /**
     * The ItemStack to put into the injection
     */
    public ItemStack getInput();

    /**
     * The resulting ItemStack after the injection
     */
    public ItemStack getOutput();

}
