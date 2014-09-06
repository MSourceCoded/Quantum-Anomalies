package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.tile.IDyeable;
import sourcecoded.quantum.tile.TileRiftNode;

import java.util.Random;

public class BlockRiftNode extends BlockQuantum implements ITileEntityProvider {

    public BlockRiftNode() {
        super();
        this.setBlockName("blockRiftNode");
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

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        ItemStack stack = player.getCurrentEquippedItem();

        if (stack != null && stack.getItem() == Items.dye) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof IDyeable) {
                ((IDyeable) tile).dye(Colourizer.match(stack.getItemDamage()));
                return true;
            }
        }

        return false;
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
        int val = (int) Math.floor(200F * explosion.explosionSize * RandomUtils.nextFloat(0.5F, 1.5F));
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
