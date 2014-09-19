package sourcecoded.quantum.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockQuantum extends ItemBlock {

    public ItemBlockQuantum(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return this.field_150939_a.getLocalizedName();
    }
}
