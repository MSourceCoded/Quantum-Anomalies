package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.quantum.tile.TileGravityNode;

public class BlockGravityNode extends BlockQuantum implements ITileEntityProvider {

    public BlockGravityNode() {
        super();
        this.setBlockName("gravityNode");
        this.setBlockTextureName("gravityNode");
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        float min = 0.3F;
        float max = 1F - min;
        this.setBlockBounds(min, min, min, max, max, max);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileGravityNode();
    }
}
