package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.tile.IDyeable;
import sourcecoded.quantum.tile.TileInjectedGlass;
import sourcecoded.quantum.tile.TileManipulation;

import java.util.Random;

public class BlockManipulation extends BlockDyeable implements ITileEntityProvider {

    public BlockManipulation() {
        super();
        this.setBlockName("blockManipulation");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
        this.setTickRandomly(true);
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (getDye(world, x, y, z) == Colourizer.LIME) {
            Block block = world.getBlock(x, y + 1, z);
            if (block != null) {
                ITileRiftHandler handler = getHandler(world, x, y, z);
                if (handler.getRiftEnergy() >= 15) {
                    block.updateTick(world, x, y + 1, z, random);
                    handler.takeRiftEnergy(15);
                }
            }
        }
    }

    ITileRiftHandler getHandler(World world, int x, int y, int z) {
        return (ITileRiftHandler) world.getTileEntity(x, y, z);
    }

    public boolean isFertile(World world, int x, int y, int z) {
        return getDye(world, x, y, z) == Colourizer.LIME;
    }


    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (getDye(world, x, y, z) == Colourizer.BLACK) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                int xO = x + dir.offsetX;
                int yO = y + dir.offsetY;
                int zO = z + dir.offsetZ;

                Block sideBlock = world.getBlock(xO, yO, zO);
                if (sideBlock != null && sideBlock instanceof BlockLiquid)
                    world.setBlockToAir(xO, yO, zO);
            }
        } else if (getDye(world, x, y, z) == Colourizer.WHITE) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                int xO = x + dir.offsetX;
                int yO = y + dir.offsetY;
                int zO = z + dir.offsetZ;

                Block sideBlock = world.getBlock(xO, yO, zO);
                ITileRiftHandler handler = getHandler(world, x, y, z);
                if (sideBlock != null && (sideBlock == Blocks.water || sideBlock == Blocks.flowing_water)) {
                    if (handler.getRiftEnergy() >= 30) {
                        world.setBlock(xO, yO, zO, Blocks.ice);
                        handler.takeRiftEnergy(30);
                    }
                }
            }
        }
    }

    public int getRenderType() {
        return AdvancedTileProxy.renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return getDye((World) world, x, y, z) == Colourizer.RED;
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        return getDye((World) world, x, y, z) == Colourizer.LIME;
    }

    Colourizer getDye(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            IDyeable dyeable = (IDyeable) tile;
            return dyeable.getColour();
        }
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileManipulation();
    }
}
