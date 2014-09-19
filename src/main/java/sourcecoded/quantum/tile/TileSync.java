package sourcecoded.quantum.tile;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.api.tileentity.IBindable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TileSync extends TileDyeable implements IBindable {

    int lastHashcode;
    int lastMeta;
    Block lastBlock;
    boolean blockExistedLastTick;

    int boundX, boundY, boundZ;

    @Override
    public void updateEntity() {
        super.updateEntity();

        doThing();
    }

    public void doThing() {
        if (!worldObj.isRemote) {

            TileEntity tile = worldObj.getTileEntity(boundX, boundY, boundZ);

            if (tile != null && tile instanceof TileSync) {
                TileSync bound = (TileSync) tile;

                boolean metaChanged = hasMetaChanged();
                boolean blockChanged = hasBlockChanged();

                if (blockChanged || metaChanged) {
                    bound.changeBlock(lastBlock, lastMeta);
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
        if (tileOld != null && tileOld instanceof TileSync)
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
}
