package sourcecoded.quantum.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.IRiftMultiplier;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.tile.TileRiftNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public strictfp class WorldUtils {

    public static List searchForBlock(World world, int xOrigin, int yOrigin, int zOrigin, int xRadius, int yRadius, int zRadius, Class type) {
        ArrayList list = new ArrayList();

        for (int x = -xRadius; x <= xRadius; x++) {
            for (int y = -yRadius; y <= yRadius; y++) {
                for (int z = -zRadius; z <= zRadius; z++) {
                    if (x == xOrigin && y == yOrigin && z == zOrigin) continue;
                    Block block = world.getBlock(xOrigin - x, yOrigin - y, zOrigin - z);
                    if (block != null && type.isAssignableFrom(block.getClass()))
                        list.add(block);
                }
            }
        }

        return list;
    }

    public static List searchForTile(World world, int xOrigin, int yOrigin, int zOrigin, int xRadius, int yRadius, int zRadius, Class type) {
        ArrayList list = new ArrayList();

        for (int x = -xRadius; x <= xRadius; x++) {
            for (int y = -yRadius; y <= yRadius; y++) {
                for (int z = -zRadius; z <= zRadius; z++) {
                    if ((xOrigin - x) == xOrigin && (yOrigin - y) == yOrigin && (zOrigin - z) == zOrigin) continue;
                    TileEntity tile = world.getTileEntity(xOrigin - x, yOrigin - y, zOrigin - z);
                    if (tile != null && type.isAssignableFrom(tile.getClass()))
                        list.add(tile);
                }
            }
        }

        return list;
    }

    public static void generateRiftNode(World world, int x, int y, int z) {
        world.setBlock(x, y, z, QABlocks.RIFT_NODE.getBlock());
        TileRiftNode node = (TileRiftNode) world.getTileEntity(x, y, z);
        node.riftStorage.giveRiftEnergy(RandomUtils.nextInt(6500, 10000));
    }

    public static float getMultiplication(World world, int xOrigin, int yOrigin, int zOrigin, int xRadius, int yRadius, int zRadius, IRiftMultiplier.RiftMultiplierTypes type) {
        List<IRiftMultiplier> multipliers = searchForBlock(world, xOrigin, yOrigin, zOrigin, xRadius, yRadius, zRadius, IRiftMultiplier.class);
        float total = 1F;

        for (IRiftMultiplier current : multipliers) {
            total *= current.getRiftMultiplication(type);
        }

        return total;
    }


}
