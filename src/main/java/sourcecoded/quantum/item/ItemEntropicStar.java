package sourcecoded.quantum.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.vacuum.recipes.VacuumEntropicStar;

public class ItemEntropicStar extends ItemQuantum implements ICraftableItem {

    public ItemEntropicStar() {
        this.setTextureName("star/starAll");
        this.setUnlocalizedName("itemEntropicStar");
        setMaxDamage(0);
        setMaxStackSize(8);
    }

    public boolean hasEffect(ItemStack itemStack, int pass) {
        return true;
    }


    @Override
    public IRecipe[] getRecipes(Item item) {
        VacuumRegistry.addRecipe(new VacuumEntropicStar());
        return new IRecipe[0];
    }
}
