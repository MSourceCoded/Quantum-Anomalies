package sourcecoded.quantum.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.injection.IInjectorRecipe;

public class ItemInjectedString extends ItemQuantum implements IInjectorRecipe {

    public ItemInjectedString() {
        this.setUnlocalizedName("itemInjectedString");
        this.setTextureName("string");
    }

    @Override
    public int getEnergyRequired() {
        return 20000;
    }

    @Override
    public byte getTier() {
        return 2;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Items.string);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this);
    }
}
