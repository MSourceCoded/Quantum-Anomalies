package sourcecoded.quantum.tile;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.api.tileentity.IBindable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TileSync extends TileDyeable implements IBindable, ITileRiftHandler {

    int lastHashcode;
    int lastMeta;
    Block lastBlock;
    boolean blockChangedLastTick;

    int boundX, boundY, boundZ;

    RiftEnergyStorage rift;

    public TileSync() {
        rift = new RiftEnergyStorage(50000);
    }


    @Override
    public void updateEntity() {
        super.updateEntity();

        doThing();
    }

    public void doThing() {
        if (!worldObj.isRemote) {

            TileEntity tile = getBoundTile();

            if (tile != null) {
                TileSync bound = (TileSync) tile;

                boolean metaChanged = hasMetaChanged();
                boolean blockChanged = hasBlockChanged();

                if (blockChanged && getRiftEnergy() >= 5000) {
                    bound.changeBlock(lastBlock, lastMeta);
                    takeRiftEnergy(5000);
                } else if (blockChanged) {
                    if (!blockChangedLastTick) {
                        for (ItemStack stack : getBlockAbove().getDrops(worldObj, xCoord, yCoord + 1, zCoord, lastMeta, 0))
                            worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, stack));
                    }
                    blockChangedLastTick = false;
                    worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
                    bound.changeBlock(Blocks.air, 0);
                }
                if (metaChanged && getRiftEnergy() >= 3000) {
                    bound.changeBlock(lastBlock, lastMeta);
                    takeRiftEnergy(5000);
                }
            }
        }
    }

    public void onDestroy() {
        TileSync tile = getBoundTile();
        if (tile != null) {
            tile.invalidate();
            tile.worldObj.setBlockToAir(tile.xCoord, tile.yCoord + 1, tile.zCoord);
        }

    }

    public void combineNBT(NBTTagCompound original, NBTTagCompound compare) {
        HashMap<String, NBTBase> compareTags = getNBTAsHashmap(compare);

        for (Map.Entry<String, NBTBase> entry : compareTags.entrySet())
            original.setTag(entry.getKey(), entry.getValue());
    }

    public HashMap<String, NBTBase> getNBTAsHashmap(NBTTagCompound compound) {
        HashMap<String, NBTBase> map = null;
        Class nbt = compound.getClass();
        Field keys;
        try {
            keys = nbt.getDeclaredField("tagMap");
            keys.setAccessible(true);
            map = (HashMap<String, NBTBase>) keys.get(compound);
        } catch (Exception e) {
        }

        return map;
    }

    public void onBlockChanged() {
        updateEntity();
    }

    public void changeBlock(Block block, int meta) {
        blockChangedLastTick = true;
        if (block instanceof ITileEntityProvider)
            worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
        else
            worldObj.setBlock(xCoord, yCoord + 1, zCoord, block, meta, 3);
    }

    public TileSync getBoundTile() {
        TileEntity tile = worldObj.getTileEntity(boundX, boundY, boundZ);
        if (tile != null && tile instanceof TileSync)
            return (TileSync) tile;
        return null;
    }

    public Block getBlockAbove() {
        return worldObj.getBlock(xCoord, yCoord + 1, zCoord);
    }

    public boolean hasMetaChanged() {
        int newMeta = worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord);
        if (newMeta != lastMeta) {
            lastMeta = newMeta;
            return true;
        }
        return false;
    }

    public boolean hasBlockChanged() {
        Block block = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
        if (block != null) {
            if (block != lastBlock) {
                lastBlock = block;
                return true;
            }
        }
        return false;
    }

    public boolean hasNBTChanged() {
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (tile != null) {
            NBTTagCompound compound = new NBTTagCompound();
            tile.writeToNBT(compound);
            int hash = compound.hashCode();
            if (hash != lastHashcode) {
                lastHashcode = hash;
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("boundX", boundX);
        compound.setInteger("boundY", boundY);
        compound.setInteger("boundZ", boundZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        boundX = compound.getInteger("boundX");
        boundY = compound.getInteger("boundY");
        boundZ = compound.getInteger("boundZ");
    }

    public void clearBinding() {
        boundX = 0;
        boundY = 0;
        boundZ = 0;
    }

    @Override
    public boolean tryBind(int x, int y, int z, boolean silent) {
        TileEntity tileOld = getBoundTile();
        if (tileOld != null)
            ((TileSync) tileOld).clearBinding();

        TileEntity tile = worldObj.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileSync) {
            boundX = x;
            boundY = y;
            boundZ = z;

            if (!silent)
                getBoundTile().tryBind(xCoord, yCoord, zCoord, true);
            return true;
        } else
            return false;
    }

    @Override
    public int takeRiftEnergy(int amount) {
        update();
        return rift.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        update();
        return rift.giveRiftEnergy(amount);
    }

    @Override
    public int getRiftEnergy() {
        return rift.getRiftEnergy();
    }

    @Override
    public int getMaxRiftEnergy() {
        return rift.getMaxRiftEnergy();
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return EnergyBehaviour.DRAIN;
    }
}
