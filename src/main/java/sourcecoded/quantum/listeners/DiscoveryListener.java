package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
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
            } else if (event.block == Blocks.diamond_ore) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.PICKAXE.get().getKey(), event.getPlayer(), false);
            } else if (event.block == Blocks.log && RandomUtils.rnd.nextInt(4) == 0) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.AXE.get().getKey(), event.getPlayer(), false);
            } else if (event.block == Blocks.mycelium) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.SHOVEL.get().getKey(), event.getPlayer(), false);
            }
        }
    }

    @SubscribeEvent
    public void craftEvent(PlayerEvent.ItemCraftedEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return;

        if (RandomUtils.nextInt(0, 4) == 0) {
            DiscoveryManager.unlockItem(QADiscoveries.Item.ARRANGEMENT.get().getKey(), event.player, false);
        }

        if (event.crafting.getItem() == QAItems.JOURNAL.getItem())
            DiscoveryManager.unlockItem(QADiscoveries.Item.DISCOVERY.get().getKey(), event.player, false);

        if (event.crafting.getItem() == Item.getItemFromBlock(Blocks.ender_chest))
            DiscoveryManager.unlockItem(QADiscoveries.Item.PLAYER.get().getKey(), event.player, false);
    }

    @SubscribeEvent
    public void entityHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer && event.source == DamageSource.outOfWorld && ((EntityPlayer) event.entityLiving).posY < 0 && isServer()) {
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

    @SubscribeEvent
    public void boneMeal(BonemealEvent event) {
        if (!event.world.isRemote && event.entityPlayer != null)
            DiscoveryManager.unlockItem(QADiscoveries.Item.MANIPULATION.get().getKey(), event.entityPlayer, false);
    }

    @SubscribeEvent(receiveCanceled = true)
    public void achievement(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (isServer())
            DiscoveryManager.unlockItem(QADiscoveries.Item.SYNC.get().getKey(), event.player, false);
    }

    @SubscribeEvent
    public void livingAttacked(AttackEntityEvent event) {
        if (event.target instanceof EntityDragon && isServer()) {
            DiscoveryManager.unlockItem(QADiscoveries.Item.DECEPTION.get().getKey(), event.entityPlayer, false);
        }
    }

    @SubscribeEvent
    public void livingKilled(LivingDeathEvent event) {
        Entity killer = event.source.getEntity();
        EntityLivingBase target = event.entityLiving;
        if (isServer() && killer != null && killer instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) killer;

            if (target instanceof EntityWither) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.STAR.get().getKey(), player, false);
            } else if (target instanceof EntityZombie) {
                ItemStack equip = ((EntityZombie) target).getEquipmentInSlot(0);
                if (equip != null && equip.getItem() instanceof ItemSword)
                    DiscoveryManager.unlockItem(QADiscoveries.Item.SWORD.get().getKey(), player, false);
            } else if (target instanceof EntityBlaze) {
                DiscoveryManager.unlockItem(QADiscoveries.Item.TOOLS.get().getKey(), player, false);
            }
        }
    }

    @SubscribeEvent
    public void arrow(ArrowLooseEvent event) {
        if (isServer() && RandomUtils.rnd.nextInt(20) == 0) {
            DiscoveryManager.unlockItem(QADiscoveries.Item.BOW.get().getKey(), event.entityPlayer, false);
        }
    }

    @SubscribeEvent
    public void pickup(PlayerEvent.ItemPickupEvent event) {
        if (isServer() && RandomUtils.rnd.nextInt(10) == 0)
            DiscoveryManager.unlockItem(QADiscoveries.Item.MAGNET.get().getKey(), event.player, false);
    }

    @SubscribeEvent
    public void hurt(LivingHurtEvent event) {
        if (isServer() && event.entityLiving instanceof EntityPlayer && RandomUtils.rnd.nextInt(10) == 0)
            DiscoveryManager.unlockItem(QADiscoveries.Item.ARMOR.get().getKey(), (EntityPlayer) event.entityLiving, false);
    }

    @SubscribeEvent
    public void interact(EntityInteractEvent event) {
        ItemStack stack = event.entityPlayer.getHeldItem();
        if (isServer() && stack != null && stack.getItem() == Items.name_tag)
            DiscoveryManager.unlockItem(QADiscoveries.Item.PENCIL.get().getKey(), (EntityPlayer) event.entityLiving, false);
    }

    boolean isServer() {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

}
