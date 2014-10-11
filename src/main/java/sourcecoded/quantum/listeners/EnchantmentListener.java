package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.enchantment.EnchantmentDeception;
import sourcecoded.quantum.entity.properties.PropertiesDeceptionTarget;
import sourcecoded.quantum.registry.QAEnchant;

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

}
