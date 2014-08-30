package sourcecoded.quantum.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;

public class ItemInfusedStick extends ItemQuantum implements IInjectorRecipe {

    public ItemInfusedStick() {
        this.setTextureName("infusedStick");
        this.setUnlocalizedName("itemInfusedStick");
    }

    @Override
    public int getEnergyRequired() {
        return InjectionConstants.INJECTION_STANDARD_ITEM;
    }

    @Override
    public byte getTier() {
        return 0;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Items.stick, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }
}
