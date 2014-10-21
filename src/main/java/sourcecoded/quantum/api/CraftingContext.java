package sourcecoded.quantum.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CraftingContext {

    public boolean respectsNBT = false;
    public boolean oreDictionary = true;
    public boolean respectsMeta = true;

    /**
     * Does this recipe require a specific NBT value?
     */
    public CraftingContext setRespectsNBT(boolean state) {
        this.respectsNBT = state;
        return this;
    }

    /**
     * Does this recipe use OreDictionary support?
     */
    public CraftingContext setOreDictionary(boolean state) {
        this.oreDictionary = state;
        return this;
    }

    /**
     * Does this depend on Metadata? Keep in mind this has
     * no effect on Wildcard metadata
     */
    public CraftingContext setRespectsMeta(boolean state) {
        this.respectsMeta = state;
        return this;
    }

    public static CraftingContext getStandardContext() {
        return new CraftingContext().setOreDictionary(true).setRespectsNBT(false).setRespectsMeta(true);
    }

    public boolean matches(ItemStack stack1, ItemStack stack2) {
        if (oreDictionary && oredict(stack1, stack2)) return true;

        if (!type(stack1, stack2)) return false;

        if (respectsMeta && !meta(stack1, stack2)) return false;

        if (respectsNBT && !nbt(stack1, stack2)) return false;

        return true;
    }

    boolean oredict(ItemStack s1, ItemStack s2) {
        int[] idset1 = OreDictionary.getOreIDs(s1);
        int[] idset2 = OreDictionary.getOreIDs(s2);

        for (int id : idset1)
            for (int id2 : idset2)
                if (id == id2) return true;

        return false;
    }

    boolean type(ItemStack s1, ItemStack s2) {
        return s1.getItem() == s2.getItem();
    }

    boolean meta(ItemStack s1, ItemStack s2) {
        return OreDictionary.itemMatches(s1, s2, false);
    }

    boolean nbt(ItemStack s1, ItemStack s2) {
        return ItemStack.areItemStackTagsEqual(s1, s2);
    }

}
