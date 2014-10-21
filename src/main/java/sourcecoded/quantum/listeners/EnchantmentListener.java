package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.enchantment.EnchantmentDeception;
import sourcecoded.quantum.entity.properties.PropertiesDeceptionTarget;
import sourcecoded.quantum.registry.QAEnchant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnchantmentListener {

    //Deception
    @SubscribeEvent
    public void livingSetTarget(LivingSetAttackTargetEvent event) {
        if (!(event.target instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.target;

        float totalChance = 0F;

        for (ItemStack stack : player.inventory.armorInventory) {
            EnchantmentDeception deception = (EnchantmentDeception) QAEnchant.DECEPTION.get();
            int level = EnchantmentHelper.getEnchantmentLevel(deception.effectId, stack);
            float chance = deception.chanceForLevel(level);
            totalChance += chance;
        }

        PropertiesDeceptionTarget target = (PropertiesDeceptionTarget) event.entityLiving.getExtendedProperties("deception");           //All this is to prevent recursion, as the entity will check for a target every tick if null
        if (target != null) {
            if (target.hasTarget(event.target)) {
                if (target.shouldIgnoreTarget(event.target))
                    resetTarget(event.entityLiving);
                return;
            }
        } else {
            target = new PropertiesDeceptionTarget();
            event.entityLiving.registerExtendedProperties("deception", target);
        }

        if (Math.random() <= totalChance) {
            resetTarget(event.entityLiving);
            target.injectTarget(event.target, true);
        } else
            target.injectTarget(event.target, false);

    }

    //Deception
    @SubscribeEvent
    public void playerAttackedEntity(AttackEntityEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {                                                                               //Allow for revenge attacks
            PropertiesDeceptionTarget target = (PropertiesDeceptionTarget) event.target.getExtendedProperties("deception");
            if (target != null)
                target.injectTarget(event.entityLiving, false);
        }
    }

    public void resetTarget(EntityLivingBase entity) {
        if (entity instanceof EntityCreature)
            ((EntityCreature) entity).setAttackTarget(null);
    }

    //Magnetism
    @SubscribeEvent
    public void onHarvestBlock(BlockEvent.HarvestDropsEvent event) {
        if (event.harvester == null || event.world.isRemote) return;
        if (EnchantmentHelper.getEnchantmentLevel(QAEnchant.RANGE.get().effectId, event.harvester.getHeldItem()) < 1) return;

        String UUID = event.harvester.getUniqueID().toString();
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.addAll(event.drops);
        for (ItemStack drop : drops) {
            if (drop.stackTagCompound == null) {
                drop.stackTagCompound = new NBTTagCompound();
                drop.stackTagCompound.setBoolean("compoundExisted", false);
            }
            drop.stackTagCompound.setString("magnetism|harvestUUID", UUID);                                                             //Set the harvest entity before the drops are spawned
        }
    }

    //Magnetism
    @SubscribeEvent
    public void onEntitySpawned(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityItem && !event.world.isRemote) {
            ItemStack stack = ((EntityItem) event.entity).getEntityItem();
            if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("magnetism|harvestUUID")) {                             //Remove the Tag from the itemstack before it is picked up, so stacking can still happen
                String UUID = stack.stackTagCompound.getString("magnetism|harvestUUID");
                stack.stackTagCompound.removeTag("magnetism|harvestUUID");
                if (stack.stackTagCompound.hasKey("compoundExisted"))
                    stack.stackTagCompound = null;

                for (Object p : event.world.playerEntities) {
                    EntityPlayer player = (EntityPlayer) p;
                    if (player.getUniqueID().toString().equals(UUID)) {
                        event.entity.setPosition(player.posX, player.posY, player.posZ);

                        ((EntityItem) event.entity).delayBeforeCanPickup = 0;
                    }
                }
            }
        }
    }
}