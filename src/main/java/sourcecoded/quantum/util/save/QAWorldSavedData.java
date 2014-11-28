package sourcecoded.quantum.util.save;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.Point3D;
import sourcecoded.quantum.client.renderer.WorldLabelRenderer;
import sourcecoded.quantum.client.renderer.block.QuantumLockRender;
import sourcecoded.quantum.network.MessageClientWorldData;
import sourcecoded.quantum.network.NetworkHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class QAWorldSavedData extends WorldSavedData {

    public static final String SAVEID = "quantumAnomalies";

    public static QAWorldSavedData getInstance(World world) {
        QAWorldSavedData data = (QAWorldSavedData) world.perWorldStorage.loadData(QAWorldSavedData.class, SAVEID);
        if (data == null) return new QAWorldSavedData(SAVEID);
        return data;
    }

    public static QAWorldSavedData getInstanceClient() {
        return getInstance(getWorldClient());
    }

    public static World getWorldClient() {
        return QuantumAnomalies.proxy.getClientPlayer().getEntityWorld();
    }

    public NBTTagList lockList = new NBTTagList();
    public NBTTagList labelList = new NBTTagList();

    public QAWorldSavedData(String id) {
        super(id);
    }

    public void markForUpdate(World world) {
        world.perWorldStorage.setData(SAVEID, this);
        this.markDirty();
        if (!world.isRemote)            //SERVER
            NetworkHandler.wrapper.sendToAll(new MessageClientWorldData(this));
        else {
            QuantumLockRender.refreshCache(this);
            WorldLabelRenderer.INSTANCE.update(this, world);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        if (tags.hasKey("QLocked"))
            lockList = (NBTTagList) tags.getTag("QLocked");

        if (tags.hasKey("Labels"))
            labelList = (NBTTagList) tags.getTag("Labels");
    }

    @Override
    public void writeToNBT(NBTTagCompound tags) {
        tags.setTag("QLocked", lockList);
        tags.setTag("Labels", labelList);
    }

    public void injectBlockLabel(World world, int x, int y, int z, String label) {
        destroyBlockLabel(world, x, y, z, false);

        NBTTagCompound compoundTag = new NBTTagCompound();
        compoundTag.setInteger("x", x);
        compoundTag.setInteger("y", y);
        compoundTag.setInteger("z", z);
        compoundTag.setString("label", label);

        labelList.appendTag(compoundTag);
        markForUpdate(world);
    }

    public void destroyBlockLabel(World world, int x, int y, int z, boolean update) {
        for (int i = 0; i < labelList.tagCount(); i++) {
            NBTTagCompound compound = labelList.getCompoundTagAt(i);
            int cx = compound.getInteger("x");
            int cy = compound.getInteger("y");
            int cz = compound.getInteger("z");

            if (x == cx && y == cy && z == cz) {
                labelList.removeTag(i);
                if (update)
                    markForUpdate(world);
            }
        }
    }

    public HashMap<Point3D, String> getLabels(World world) {
        HashMap<Point3D, String> labels = new HashMap<Point3D, String>();

        for (int i = 0; i < labelList.tagCount(); i++) {
            NBTTagCompound compound = labelList.getCompoundTagAt(i);
            int cx = compound.getInteger("x");
            int cy = compound.getInteger("y");
            int cz = compound.getInteger("z");

            labels.put(new Point3D(cx, cy, cz), compound.getString("label"));
        }

        return labels;
    }

    public void injectQuantumLock(World world, Block block, int meta, int x, int y, int z) {
        destroyQuantumLock(world, x, y, z, false);

        String blockName = Block.blockRegistry.getNameForObject(block);

        NBTTagCompound compoundTag = new NBTTagCompound();
        compoundTag.setString("blockID", blockName);
        compoundTag.setInteger("meta", meta);
        compoundTag.setInteger("x", x);
        compoundTag.setInteger("y", y);
        compoundTag.setInteger("z", z);

        lockList.appendTag(compoundTag);
        markForUpdate(world);
    }

    public void destroyQuantumLock(World world, int x, int y, int z, boolean update) {
        for (int i = 0; i < lockList.tagCount(); i++) {
            NBTTagCompound compound = lockList.getCompoundTagAt(i);
            int cx = compound.getInteger("x");
            int cy = compound.getInteger("y");
            int cz = compound.getInteger("z");

            if (x == cx && y == cy && z == cz) {
                lockList.removeTag(i);
                if (update)
                    markForUpdate(world);
            }
        }
    }

    public Map.Entry<Block, Integer> retrieveQuantumLock(int x, int y, int z) {
        for (int i = 0; i < lockList.tagCount(); i++) {
            NBTTagCompound compound = lockList.getCompoundTagAt(i);
            int cx = compound.getInteger("x");
            int cy = compound.getInteger("y");
            int cz = compound.getInteger("z");

            if (x == cx && y == cy && z == cz)
                return new AbstractMap.SimpleEntry<Block, Integer>(Block.getBlockFromName(compound.getString("blockID")), compound.getInteger("meta"));
        }

        return null;
    }
}
