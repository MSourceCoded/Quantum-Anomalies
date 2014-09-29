package sourcecoded.quantum.api.vacuum;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IVacuumRecipe {

    public List<ItemStack> getIngredients();

    public List<ItemStack> getCatalysts();

    public List<ItemStack> getOutputs();

    public int getVacuumEnergyStart();

    public int getVacuumEnergyPerItem();

    public Instability getInstabilityLevel();
}
