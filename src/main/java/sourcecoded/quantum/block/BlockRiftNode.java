package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.tile.TileRiftNode;

public class BlockRiftNode extends BlockQuantum implements ITileEntityProvider {

    public BlockRiftNode() {
        super();
        this.setBlockName("riftNode");
        this.setBlockTextureName("haze");
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        float min = 0.3F;
        float max = 1F - min;
        this.setBlockBounds(min, min, min, max, max, max);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
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
        return new TileRiftNode();
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
        int val = (int) Math.floor(100F * explosion.explosionSize * RandomUtils.nextFloat(0.5F, 1.5F));
        TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
        if (node != null) {
            if (node.shockCooldown == 0)
                node.giveRiftEnergy(val);
            node.shockCooldown = node.maxShockCooldown;
        }
    }
}
