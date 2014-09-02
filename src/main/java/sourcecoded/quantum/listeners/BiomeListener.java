package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.EntityEvent;
import sourcecoded.quantum.Achievements;
import sourcecoded.quantum.api.worldgen.IBiomeAnomaly;
import sourcecoded.quantum.network.MessageAchievement;
import sourcecoded.quantum.network.NetworkHandler;

public class BiomeListener {

    @SubscribeEvent
    public void enterChunk(EntityEvent.EnteringChunk event) {
        if (event.entity instanceof EntityPlayer) {
            BiomeGenBase biome = event.entity.worldObj.getBiomeGenForCoords(event.newChunkX * 16, event.newChunkZ * 16);

            if (biome instanceof IBiomeAnomaly)
                NetworkHandler.wrapper.sendToServer(new MessageAchievement(Achievements.learning.statId));
        }
    }

}
