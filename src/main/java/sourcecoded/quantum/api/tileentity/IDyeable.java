package sourcecoded.quantum.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.block.Colourizer;

public interface IDyeable {

    public void dye(Colourizer colour);

    public Colourizer getColour();

    public static class DyeUtils {

        public static boolean attemptDye(EntityPlayer player, World world, int x, int y, int z) {
            ItemStack stack = player.getCurrentEquippedItem();

            if (stack != null && stack.getItem() == Items.dye) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile != null && tile instanceof IDyeable) {
                    Colourizer color = Colourizer.match(stack.getItemDamage());
                    ((IDyeable) tile).dye(color);
                    return true;
                }
            }

            if (stack != null && stack.getItem() == Items.nether_star) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile != null && tile instanceof IDyeable) {
                    ((IDyeable) tile).dye(Colourizer.RAINBOW);
                    return true;
                }
            }

            return false;
        }
    }

}
