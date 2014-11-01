package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.block.BlockRiftNode;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DiscoveryListener {

    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == null) return;

        if (!event.world.isRemote) {
            if (event.block instanceof BlockRiftNode) {
                DiscoveryManager.unlockCategory(QADiscoveries.Category.BLOCKS.get().getKey(), event.getPlayer());
                DiscoveryManager.unlockItem(QADiscoveries.Item.NODE.get().getKey(), event.getPlayer(), false);
            } else if (event.block == Blocks.obsidian && RandomUtils.nextInt(0, 5) == 0) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.ETCHED_STONE.get().getKey(), event.getPlayer(), false);
                DiscoveryManager.unlockItem(QADiscoveries.Item.ETCHED_CORNER.get().getKey(), event.getPlayer(), false);
            }
        }
    }

    @SubscribeEvent
    public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return;

        if (RandomUtils.nextInt(0, 4) == 0) {
            DiscoveryManager.unlockItem(QADiscoveries.Item.ARRANGEMENT.get().getKey(), event.player, false);
            DiscoveryManager.revealChildren(QADiscoveries.Item.ARRANGEMENT.get().getKey(), event.player);
        }

        if (event.crafting.getItem() == QAItems.JOURNAL.getItem())
            DiscoveryManager.unlockItem(QADiscoveries.Item.DISCOVERY.get().getKey(), event.player, false);
    }

}
