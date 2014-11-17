package sourcecoded.quantum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.util.save.QAWorldSavedData;

public class ItemPencil extends ItemQuantum {

    public ItemPencil() {
        this.setTextureName("pencil");
        this.setUnlocalizedName("itemRiftPencil");
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            String itemName = stack.getDisplayName();

            if (player.isSneaking())
                QAWorldSavedData.getInstance(world).destroyBlockLabel(world, x, y, z, true);
            else if (!itemName.equals(this.getItemStackDisplayName(stack)))
                QAWorldSavedData.getInstance(world).injectBlockLabel(world, x, y, z, itemName);

            return true;
        }
        return false;
    }
}