package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.event.world.BlockEvent;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.block.BlockRiftNode;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DiscoveryListener {

    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null) return;

        if (!event.world.isRemote) {
            if (event.block instanceof BlockRiftNode) {
                DiscoveryManager.unlockCategory(QADiscoveries.Category.BLOCKS.get().getKey(), event.getPlayer());
                DiscoveryManager.unlockItem(QADiscoveries.Item.NODE.get().getKey(), event.getPlayer(), false);
            }
        }
    }

    @SubscribeEvent
    public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return;

        DiscoveryManager.unlockItem(QADiscoveries.Item.ARRANGEMENT.get().getKey(), event.player, false);
    }

}
