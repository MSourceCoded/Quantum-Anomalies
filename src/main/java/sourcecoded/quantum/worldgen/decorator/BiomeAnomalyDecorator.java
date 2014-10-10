package sourcecoded.quantum.worldgen.decorator;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import sourcecoded.quantum.util.WorldUtils;

public class BiomeAnomalyDecorator extends BiomeDecorator {

    public BiomeAnomalyDecorator() {
    }

    protected void genDecorations(BiomeGenBase p_150513_1_) {
        if (this.randomGenerator.nextInt(15) == 0) {
            tryGenerate(p_150513_1_);
        }
    }

    void tryGenerate(BiomeGenBase p_150513_1_) {
        int i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
        int j = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
        int k = -1;
        while (k == -1) {
            int w = this.randomGenerator.nextInt(216) + 40;
            if (currentWorld.isAirBlock(i, w, j))
                k = w;
        }

        WorldUtils.generateRiftNode(currentWorld, i, k, j);
    }
}
