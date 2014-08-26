package sourcecoded.quantum.worldgen.feature;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class FeaturePillarNode extends WorldGenerator {

    private Block filter;
    private Block pillarType;
    private Class<? extends Entity> entity;
    private static final String __OBFID = "CL_00000433";

    //Literally copy-pasted from WorldGenSpike, just with some extra arguments. Don't ask how it works, I have no idea

    public FeaturePillarNode(Block filter, Block pillarType, Class<? extends Entity> entity) {
        this.filter = filter;
        this.pillarType = pillarType;
        this.entity = entity;
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
        if (world.isAirBlock(x, y, z) && world.getBlock(x, y - 1, z) == this.filter) {
            int l = rand.nextInt(32) + 6;
            int i1 = rand.nextInt(4) + 1;
            int j1;
            int k1;
            int l1;
            int i2;

            for (j1 = x - i1; j1 <= x + i1; ++j1) {
                for (k1 = z - i1; k1 <= z + i1; ++k1) {
                    l1 = j1 - x;
                    i2 = k1 - z;

                    if (l1 * l1 + i2 * i2 <= i1 * i1 + 1 && world.getBlock(j1, y - 1, k1) != this.filter) {
                        return false;
                    }
                }
            }

            for (j1 = y; j1 < y + l && j1 < 256; ++j1) {
                for (k1 = x - i1; k1 <= x + i1; ++k1) {
                    for (l1 = z - i1; l1 <= z + i1; ++l1) {
                        i2 = k1 - x;
                        int j2 = l1 - z;

                        if (i2 * i2 + j2 * j2 <= i1 * i1 + 1) {
                            world.setBlock(k1, j1, l1, pillarType, 0, 2);
                        }
                    }
                }
            }

            try {
                Entity entityToSpawn = (Entity)entity.getConstructor(new Class[]{World.class}).newInstance(world);
                entityToSpawn.setLocationAndAngles((double) ((float) x + 0.5F), (double) (y + l), (double) ((float) z + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntityInWorld(entityToSpawn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
