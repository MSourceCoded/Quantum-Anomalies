package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.tile.TileRiftNode;

import java.util.Random;

public class BlockRiftNode extends BlockDyeable implements ITileEntityProvider {

    public BlockRiftNode() {
        super();
        this.setBlockName("blockRiftNode");
        this.setBlockTextureName("haze");
        this.setCreativeTab(null);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        float min = 0.3F;
        float max = 1F - min;
        this.setBlockBounds(min, min, min, max, max, max);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            ITileRiftHandler rift = (ITileRiftHandler) tile;
            float perc = (float)rift.getRiftEnergy() / (float)rift.getMaxRiftEnergy();
            return (int)(perc * 15);
        }

        return 0;
    }

    @Override
    public int getRenderType() {
        return SimpleTileProxy.renderID;
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
        double dist = Math.sqrt(Math.pow(x - explosion.explosionX, 2) + Math.pow(z - explosion.explosionZ, 2));
        int val = (int) Math.floor(5000 * (explosion.explosionSize / dist) * RandomUtils.nextFloat(0.5F, 1.5F));        //Explosion val
        TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
        if (node != null && node.colour == Colourizer.ORANGE) {
            if (node.shockCooldown <= 0 && node.giveRiftEnergy(val) != 0) {
                    if (world.isRemote)
                        for (int i = 0; i < 10; i++)
                            world.spawnParticle("flame", x + 0.5, y + 0.5, z + 0.5, RandomUtils.nextDouble(-0.1, 0.1), 0.1D, RandomUtils.nextDouble(-0.1, 0.1));
            }
            node.shockCooldown = node.maxShockCooldown;
        }
    }

    public int quantityDropped(Random rnd) {
        return 0;
    }
}
