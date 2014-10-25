package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.Point3D;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.api.arrangement.ItemMatrix;
import sourcecoded.quantum.api.event.crafting.ArrangementCraftingEvent;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.network.MessageBlockBreakFX;
import sourcecoded.quantum.network.MessageVanillaParticle;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.structure.MultiblockLayer;
import sourcecoded.quantum.tile.TileArrangement;

import java.util.ArrayList;
import java.util.List;

public class BlockArrangement extends BlockDyeable implements ITileEntityProvider {

    public static MultiblockLayer multiblock = new MultiblockLayer(
            "oao", "aca", "oao", 'o', Blocks.obsidian, 'c', Blocks.crafting_table, 'a', Blocks.air
    );

    public BlockArrangement() {
        super();
        this.setBlockName("blockMatterArrangementTable");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        boolean t = super.onBlockActivated(world, x, y, z, player, side, xo, yo, zo);

        if (!t)
            tryCraft(world, x, y, z, true, player);
        return true;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
        int l = world.getBlockMetadata(x, y, z);
        boolean flag1 = (l & 8) != 0;

        if (powered && !flag1) {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
            tryCraft(world, x, y, z, true, null);
        } else if (!powered && flag1) {
            world.setBlockMetadataWithNotify(x, y, z, l & -9, 4);
        }
    }

    @SuppressWarnings("unchecked")
    public Object tryCraft(World world, int xO, int yO, int zO, boolean destroy, EntityPlayer player) {
        ItemMatrix matrix = new ItemMatrix(3, 3);
        ArrayList<Point3D> destroyPoints = new ArrayList<Point3D>();

        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++) {
                int nx = xO + x;
                int nz = zO + z;
                int y = yO;

                if (nx == xO && nz == zO) y += 1;   //Centre

                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(nx, y, nz, nx + 1, y + 1, nz + 1));
                if (items.size() == 1) {
                    ItemStack is = items.get(0).getEntityItem();
                    matrix.setItemAt(x + 1, z + 1, is);
                } else {
                    Block block = world.getBlock(nx, y, nz);
                    if (block != null && !block.isAir(world, nx, y, nz)) {
                        matrix.setItemAt(x + 1, z + 1, new ItemStack(block));
                        destroyPoints.add(new Point3D(nx, y, nz));
                    }
                }
            }

        IArrangementRecipe recipe = ArrangementRegistry.getRecipe(matrix);

        IRecipe vanillaRecipe = ArrangementRegistry.hasVanillaRecipeForMatrix(matrix, world);

        if (destroy && !world.isRemote) {
            if (recipe != null && !QuantumAPI.eventBus.post(new ArrangementCraftingEvent.Initiated(recipe, player, world, world.getTileEntity(xO, yO, zO)))) {
                destroyMatrix(world, xO, yO, zO, destroyPoints);

                ItemStack result = recipe.getOutput();

                completeCraft(result, world, xO, yO, zO);

                QuantumAPI.eventBus.post(new ArrangementCraftingEvent.Complete(recipe, player, world, world.getTileEntity(xO, yO, zO)));
            } else if (vanillaRecipe != null) {
                destroyMatrix(world, xO, yO, zO, destroyPoints);

                ItemStack result = vanillaRecipe.getRecipeOutput();

                completeCraft(result, world, xO, yO, zO);
            } else {
                NetworkHandler.wrapper.sendToDimension(new MessageVanillaParticle("mobSpell", xO + 0.5, yO + 1, zO + 0.5, 1D, 0D, 0D, 1), world.provider.dimensionId);
            }
        }

        return recipe == null ? vanillaRecipe : recipe;
    }

    @SuppressWarnings("unchecked")
    public void destroyMatrix(World world, int xO, int yO, int zO, ArrayList<Point3D> destroyPoints) {
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++) {
                int nx = xO + x;
                int nz = zO + z;
                int y = yO;

                if (nx == xO && nz == zO) y += 1;   //Centre

                for (Point3D point : destroyPoints) {
                    if (point.getX() == nx && point.getY() == y && point.getZ() == nz) {
                        NetworkHandler.wrapper.sendToDimension(new MessageBlockBreakFX(nx, y, nz), world.provider.dimensionId);
                        world.setBlockToAir(nx, y, nz);
                    }
                }

                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(nx, y, nz, nx + 1, y + 1, nz + 1));
                if (items.size() == 1)
                    items.get(0).getEntityItem().stackSize -= 1;
            }
    }

    public void completeCraft(ItemStack result, World world, int xO, int yO, int zO) {
        if (result.stackSize == 0)
            result.stackSize = 1;

        Entity entity = new EntityItem(world, xO + 0.5, yO + 1.25, zO + 0.5, result);

        if (result.getItem().hasCustomEntity(result))
            entity = result.getItem().createEntity(world, entity, result);

        entity.motionX = 0;
        entity.motionZ = 0;

        world.spawnEntityInWorld(entity);
        for (int i1 = 0; i1 < 9; ++i1) {
            double d0 = RandomUtils.rnd.nextGaussian() * 0.02D;
            double d1 = RandomUtils.rnd.nextGaussian() * 0.02D;
            double d2 = RandomUtils.rnd.nextGaussian() * 0.02D;
            NetworkHandler.wrapper.sendToDimension(new MessageVanillaParticle("happyVillager", xO + RandomUtils.rnd.nextFloat(), yO + 1 + RandomUtils.nextFloat(0F, 0.3F), zO + RandomUtils.rnd.nextFloat(), d0, d1, d2, 1), world.provider.dimensionId);
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
        return side == ForgeDirection.DOWN || side == ForgeDirection.UP;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileArrangement();
    }
}
