package sourcecoded.quantum.worldgen.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import sourcecoded.quantum.worldgen.decorator.BiomeHellAnomalyDecorator;

@SuppressWarnings("unchecked")
public class BiomeHellAnomaly extends BiomeGenBase {

    public BiomeHellAnomaly(int id) {
        super(id);
        this.setBiomeName("Hell Anomaly");

        this.rootHeight = 1F;
        this.heightVariation = 2F;

        this.setColor(16711680);
        this.setDisableRain();

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
        this.topBlock = Blocks.netherrack;
        this.fillerBlock = Blocks.netherrack;
        this.theBiomeDecorator = new BiomeHellAnomalyDecorator();
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float temperature) {
        return 16711680;
    }
}
