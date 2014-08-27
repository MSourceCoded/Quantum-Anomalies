package sourcecoded.quantum.api.injection;

import net.minecraft.item.ItemStack;

public interface IInjectorRecipe {

    /**
     * Get the amount of energy required for the injection.
     */
    public int getEnergyRequired();

    /**
     * Get the Tier of the injection.
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
