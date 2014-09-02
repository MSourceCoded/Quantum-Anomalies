package sourcecoded.quantum.worldgen.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import sourcecoded.quantum.api.worldgen.IBiomeAnomaly;
import sourcecoded.quantum.worldgen.decorator.BiomeEndAnomalyDecorator;

@SuppressWarnings("unchecked")
public class BiomeEndAnomaly extends BiomeGenBase implements IBiomeAnomaly {

    public BiomeEndAnomaly(int id) {
        super(id);
        this.setBiomeName("End Anomaly");

        this.rootHeight = 0.6F;
        this.heightVariation = 1.5F;

        this.setColor(8421631);
        this.setDisableRain();

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = Blocks.end_stone;
        this.fillerBlock = Blocks.end_stone;
        this.theBiomeDecorator = new BiomeEndAnomalyDecorator();
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature) {
        return 0;
    }
}
