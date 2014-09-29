package sourcecoded.quantum.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.block.Colourizer;

/**
 * An interface for use with a TileEntity that can
 * be dyed. Handling HOW the TileEntity is died is up
 * to you, but a static method is encased in a sub-class
 * to implement on your Block class if you want it to
 * trigger.
 *
 * Keep in mind that saving this data to NBT is left
 * up to you, but I recommend saving the ordinal value
 * of the Colourizer object.
 *
 * @see sourcecoded.quantum.api.tileentity.IDyeable.DyeUtils#attemptDye(net.minecraft.entity.player.EntityPlayer, net.minecraft.world.World, int, int, int)
 * @see sourcecoded.quantum.api.block.Colourizer
 *
 * @author SourceCoded
 */
public interface IDyeable {

    /**
     * This method is called when the TileEntity
     * is dyed. You should set your Colourizer
     * state in this method, and also update the TE
     * if it's required
     */
    public void dye(Colourizer colour);

    /**
     * Called for getting the Colourizer state
     * for this TileEntity. This is almost always
     * called on the Client side
     */
    public Colourizer getColour();

    /**
     * A static utility class for the IDyeable interface.
     * This is designed for use on Block classes, and will
     * notify the TileEntity at that position of the changes.
     *
     * @see sourcecoded.quantum.api.tileentity.IDyeable
     * @see sourcecoded.quantum.api.block.Colourizer
     *
     * @author SourceCoded
     */
    public static class DyeUtils {

        /**
         * Attempt to dye the TileEntity at the specified
         * location with the Dye in the EntityPlayer's hand.
         *
         * @return Was the dye successful? This can be false
         * if the player does not have an item in their hand,
         * or if the item is not a dye.
         */
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
