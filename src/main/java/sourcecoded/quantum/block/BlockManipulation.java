package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.core.crafting.ICraftableBlock;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.tileentity.IDyeable;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.crafting.vacuum.VacuumManipulation;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.tile.TileManipulation;

import java.util.Random;

public class BlockManipulation extends BlockDyeable implements ITileEntityProvider, ICraftableBlock {

    public BlockManipulation(Material mat) {
        super(mat);

        this.setBlockName("blockManipulation");
        if (mat == Material.water)
            this.setCreativeTab(null);

        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
        this.setTickRandomly(true);
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        Colourizer oldColour = getDye(world, x, y, z);
        boolean result = IDyeable.DyeUtils.attemptDye(player, world, x, y, z);
        Colourizer newColour = getDye(world, x, y, z);
        if (result && newColour == Colourizer.BLUE)
            changeBlock(world, x, y, z, QABlocks.MANIPULATION_WATER.getBlock());
        if (result && oldColour == Colourizer.BLUE && newColour != Colourizer.BLUE)
            changeBlock(world, x, y, z, QABlocks.MANIPULATION_STANDARD.getBlock());

        return result;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(QABlocks.MANIPULATION_STANDARD.getBlock());
    }

    public void changeBlock(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        world.setBlock(x, y, z, block);
        if (tile != null) {
            tile.validate();
            world.setTileEntity(x, y, z, tile);
        }
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        Colourizer dye = getDye(world, x, y, z);
        if (dye == Colourizer.LIME) {
            Block block = world.getBlock(x, y + 1, z);
            if (block != null) {
                ITileRiftHandler handler = getHandler(world, x, y, z);
                if (handler.getRiftEnergy() >= 300) {
                    block.updateTick(world, x, y + 1, z, random);
                    handler.takeRiftEnergy(300);
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
                    if (handler.getRiftEnergy() >= 500) {
                        world.setBlock(xO, yO, zO, Blocks.ice);
                        handler.takeRiftEnergy(500);
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

    Colourizer getDye(IBlockAccess world, int x, int y, int z) {
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

    @Override
    public IRecipe[] getRecipes(Block block) {
        VacuumRegistry.addRecipe(new VacuumManipulation());
        return new IRecipe[0];
    }
}
