package sourcecoded.quantum.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import sourcecoded.quantum.api.tileentity.IDyeable;

public class BlockDyeable extends BlockQuantum {

    public BlockDyeable() {
        super();
    }

    public BlockDyeable(Material mat) {
        super(mat);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        return IDyeable.DyeUtils.attemptDye(player, world, x, y, z);
    }
}
