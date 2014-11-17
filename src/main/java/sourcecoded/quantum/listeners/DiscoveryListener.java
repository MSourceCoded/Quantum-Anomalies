package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ChunkCoordComparator;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.api.translation.LocalizationUtils;
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

    @SubscribeEvent
    public void entityHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer && event.source == DamageSource.outOfWorld && ((EntityPlayer) event.entityLiving).posY < 0) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (!DiscoveryManager.itemUnlocked(QADiscoveries.Item.VACUUM.get().getKey(), player) && DiscoveryManager.areParentsUnlocked(QADiscoveries.Item.VACUUM.get().getKey(), player)) {
                String translate = "qa.aboutToVoid";
                player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours(translate, translate)));
                ChunkCoordinates spawn = player.getBedLocation(player.worldObj.provider.dimensionId);

                ChunkCoordinates portalEntrance = MinecraftServer.getServer().worldServerForDimension(player.dimension).getEntrancePortalLocation();
                if (portalEntrance != null && spawn == null) {
                    spawn = portalEntrance;
                }

                if (spawn == null) {
                    spawn = player.worldObj.getSpawnPoint();
                }

                int y = spawn.posY;

                while (!player.worldObj.isAirBlock(spawn.posX, y, spawn.posZ))      //Get top block
                    y++;

                player.setPositionAndUpdate(spawn.posX, y, spawn.posZ);
                player.fallDistance = 0F;

                DiscoveryManager.unlockItem(QADiscoveries.Item.VACUUM.get().getKey(), player, false);
            }
        }
    }

}
