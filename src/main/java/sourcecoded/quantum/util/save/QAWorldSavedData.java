package sourcecoded.quantum.util.save;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import org.apache.commons.lang3.tuple.Triple;
import sourcecoded.quantum.registry.BlockRegistry;

import java.util.AbstractMap;
import java.util.Map;

public class QAWorldSavedData extends WorldSavedData {

    public static final String SAVEID = "quantumAnomalies";

    public static QAWorldSavedData getInstance(World world) {
        QAWorldSavedData data = (QAWorldSavedData) world.perWorldStorage.loadData(QAWorldSavedData.class, SAVEID);
        if (data == null) return new QAWorldSavedData(SAVEID);
        return data;
    }

    NBTTagList lockList = new NBTTagList();

    public QAWorldSavedData(String id) {
        super(id);
    }

    public void markForUpdate(World world) {
        world.perWorldStorage.setData(SAVEID, this);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        if (tags.hasKey("QLocked"))
            lockList = (NBTTagList) tags.getTag("QLocked");
    }

    @Override
    public void writeToNBT(NBTTagCompound tags) {
        tags.setTag("QLocked", lockList);
    }

    public void injectQuantumLock(World world, Block block, int meta, int x, int y, int z) {
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

    public void destroyQuantumLock(int x, int y, int z) {
        for (int i = 0; i < lockList.tagCount(); i++) {
            NBTTagCompound compound = lockList.getCompoundTagAt(i);
            int cx = compound.getInteger("x");
            int cy = compound.getInteger("y");
            int cz = compound.getInteger("z");

            if (x == cx && y == cy && z == cz)
                lockList.removeTag(i);
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
