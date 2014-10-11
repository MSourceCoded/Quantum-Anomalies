package sourcecoded.quantum.block;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.util.save.QAWorldSavedData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockQuantumLock extends BlockQuantum {

    public BlockQuantumLock() {
        this.setBlockName("blockQuantumLock");
        this.setBlockTextureName("infusedStone");
        this.setResistance(99999F);           //That should be enough
        this.setBlockUnbreakable();
        this.setTickRandomly(true);
    }

    public boolean hasTileEntity(int meta) {
        return true;
    }

    public Block getBlock(IBlockAccess world, int x, int y, int z) {
        World w =  tryConvertWorld(world);
        Map.Entry<Block, Integer> pair = QAWorldSavedData.getInstance(w).retrieveQuantumLock(x, y, z);
        return pair.getKey();
    }

    public World tryConvertWorld(IBlockAccess iba) {
        if (iba instanceof World) return (World)iba;
        if (iba instanceof ChunkCache) {
            Field obj = ReflectionHelper.findField(ChunkCache.class, "worldObj", "e", "field_72815_e");
            try {
                return (World) obj.get(iba);
            } catch(IllegalAccessException e) {
                System.err.println("Illegal Access");
                return null;
            }
        }
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderType() {
        return -1;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
        return false;
    }

    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {}

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        world.removeTileEntity(x, y, z);
        QAWorldSavedData.getInstance(world).destroyQuantumLock(x, y, z);
        QAWorldSavedData.getInstance(world).markForUpdate(world);
    }

    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
        return false;
    }

    //-- BEGIN CODE PROXY --//

    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getEnchantPowerBonus(world, x, y, z);
    }

    public boolean getBlocksMovement(IBlockAccess iba, int x, int y, int z) {
        return getBlock(iba, x, y, z).getBlocksMovement(iba, x, y, z);
    }

    public boolean isBlockSolid(IBlockAccess iba, int x, int y, int z, int side) {
        return getBlock(iba, x, y, z).isBlockSolid(iba, x, y, z, side);
    }

    public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int side) {
        return getBlock(iba, x, y, z).getIcon(iba, x, y, z, side);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity collide) {
        getBlock(world, x, y, z).addCollisionBoxesToList(world, x, y, z, mask, list, collide);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    public void updateTick(World world, int x, int y, int z, Random r) {
        getBlock(world, x, y, z).updateTick(world, x, y, z, r);
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random r) {
        getBlock(world, x, y, z).randomDisplayTick(world, x, y, z, r);
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {          //TODO fix this method (do not proxy)
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        getBlock(world, x, y, z).onNeighborBlockChange(world, x, y, z, block);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        getBlock(world, x, y, z).onBlockAdded(world, x, y, z);
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int p1, float p2, int p3) {
        getBlock(world, x, y, z).dropBlockAsItemWithChance(world, x, y, z, p1, p2, p3);
    }

    public void dropXpOnBlockBreak(World world, int x, int y, int z, int meta) {
        getBlock(world, x, y, z).dropXpOnBlockBreak(world, x, y, z, meta);
    }

    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 v1, Vec3 v2) {
        return getBlock(world, x, y, z).collisionRayTrace(world, x, y, z, v1, v2);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return getBlock(world, x, y, z).onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        getBlock(world, x, y, z).onEntityWalking(world, x, y, z, entity);
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float xo, float yo, float zo, int meta) {
        return getBlock(world, x, y, z).onBlockPlaced(world, x, y, z, side, xo, yo, zo, meta);
    }

    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        getBlock(world, x, y, z).onBlockClicked(world, x, y, z, player);
    }

    public void velocityToAddToEntity(World world, int x, int y, int z, Entity entity, Vec3 vec) {
        getBlock(world, x, y, z).velocityToAddToEntity(world, x, y, z, entity, vec);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        getBlock(iba, x, y, z).setBlockBoundsBasedOnState(iba, x, y, z);
    }

    public int colorMultiplier(IBlockAccess iba, int x, int y, int z) {
        return getBlock(iba, x, y, z).colorMultiplier(iba, x, y, z);
    }

    public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int par1) {
        return getBlock(iba, x, y, z).isProvidingWeakPower(iba, x, y, z, par1);
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        getBlock(world, x, y, z).onEntityCollidedWithBlock(world, x, y, z, entity);
    }

    public int isProvidingStrongPower(IBlockAccess iba, int x, int y, int z, int par1) {
        return getBlock(iba, x, y, z).isProvidingStrongPower(iba, x, y, z, par1);
    }

    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        getBlock(world, x, y, z).harvestBlock(world, player, x, y, z, meta);
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).canBlockStay(world, x, y, z);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        getBlock(world, x, y, z).onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
        getBlock(world, x, y, z).onPostBlockPlaced(world, x, y, z, meta);
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int par1, int par2) {
        return getBlock(world, x, y, z).onBlockEventReceived(world, x, y, z, par1, par2);
    }

    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float dist) {
        getBlock(world, x, y, z).onFallenUpon(world, x, y, z, entity, dist);
    }

    public Item getItem(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getItem(world, x, y, z);
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getDamageValue(world, x, y, z);
    }

    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        getBlock(world, x, y, z).onBlockHarvested(world, x, y, z, meta, player);
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int par1) {
        return getBlock(world, x, y, z).getComparatorInputOverride(world, x, y, z, par1);
    }

    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return getBlock(world, x, y, z).isLadder(world, x, y, z, entity);
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return getBlock(world, x, y, z).isSideSolid(world, x, y, z, side);
    }

    public boolean isBurning(IBlockAccess world, int x, int y, int z) {
        return getBlock(world, x, y, z).isBurning(world, x, y, z);
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return getBlock(world, x, y, z).getFireSpreadSpeed(world, x, y, z, face);
    }

    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        return getBlock(world, x, y, z).isFireSource(world, x, y, z, side);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return getBlock(world, x, y, z).getDrops(world, x, y, z, metadata, fortune);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return getBlock(world, x, y, z).canSustainLeaves(world, x, y, z);
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return getBlock(world, x, y, z).isWood(world, x, y, z);
    }

    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
        return getBlock(world, x, y, z).canConnectRedstone(world, x, y, z, side);
    }

    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).canPlaceTorchOnTop(world, x, y, z);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return getBlock(world, x, y, z).getPickBlock(target, world, x, y, z);
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        return getBlock(world, x, y, z).canSustainPlant(world, x, y, z, direction, plantable);
    }

    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        getBlock(world, x, y, z).onPlantGrow(world, x, y, z, sourceX, sourceY, sourceZ);
    }

    public boolean isFertile(World world, int x, int y, int z) {
        return getBlock(world, x, y, z).isFertile(world, x, y, z);
    }

    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        return getBlock(world, x, y, z).getLightOpacity(world, x, y, z);
    }

    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return getBlock(worldObj, x, y, z).isBeaconBase(worldObj, x, y, z, beaconX, beaconY, beaconZ);
    }

    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        getBlock(world, x, y, z).onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }

    public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return getBlock(world, x, y, z).shouldCheckWeakPower(world, x, y, z, side);
    }

    public boolean getWeakChanges(IBlockAccess world, int x, int y, int z) {
        return getBlock(world, x, y, z).getWeakChanges(world, x, y, z);
    }

}
