package sourcecoded.quantum.worldgen.decorator;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import sourcecoded.quantum.entity.EntityEnderishCrystal;
import sourcecoded.quantum.worldgen.feature.FeaturePillarNode;

public class BiomeEndAnomalyDecorator extends BiomeDecorator {

    protected WorldGenerator spikeGen;
    private static final String __OBFID = "CL_00000188";

    public BiomeEndAnomalyDecorator() {
        this.spikeGen = new FeaturePillarNode(Blocks.end_stone, Blocks.obsidian, EntityEnderishCrystal.class);
    }

    protected void genDecorations(BiomeGenBase p_150513_1_) {
        if (this.randomGenerator.nextInt(5) == 0) {
            int i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int j = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int k = this.currentWorld.getTopSolidOrLiquidBlock(i, j);
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, i, k, j);
        }
    }

}
