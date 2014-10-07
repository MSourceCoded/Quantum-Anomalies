package sourcecoded.quantum.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;

/**
 * An interface for use with the Binding
 * Sceptre focus. Implement this on the tile
 * that you want the binding to be sent to.
 *
 * @author SourceCoded
 */
public interface IBindable {

    /**
     * Try to bind the block at x, y, z
     * @param silent Should the block not bind-back?
     *               (For intra-binding TEs)
     * @return was the binding successful?
     */
    public boolean tryBind(EntityPlayer player, int x, int y, int z, boolean silent);

}
