package sourcecoded.quantum.item.armor;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.listeners.FlightListener;
import sourcecoded.quantum.network.MessageVanillaParticle;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.registry.QAItems;

import java.util.List;

public class ItemRiftArmor extends ItemArmorQuantum implements ISpecialArmor {

    public final int HELM = 0;
    public final int CHEST = 1;
    public final int LEGS = 2;
    public final int BOOTS = 3;

    public ItemRiftArmor(int armorType) {
        super(ArmorMaterial.DIAMOND, 0, armorType);
        String name = "null";
        switch (armorType) {
            case HELM:
                name = "helm";
                break;
            case CHEST:
                name = "chest";
                break;
            case LEGS:
                name = "legs";
                break;
            case BOOTS:
                name = "boots";
                break;
        }

        this.setTextureName("armor/" + name);
        this.setUnlocalizedName("riftArmor_" + name);
        this.setMaxStackSize(1);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return "quantumAnomalies:textures/misc/transparent.png";
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean getIsRepairable(ItemStack thisStack, ItemStack compareStack) {
        return false;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        int priority = 1;
        double ratio = 3D;
        int protection = 20;

        protection = handleSource(player, armor, source, damage, slot);

        if (source.canHarmInCreative())
            return new ArmorProperties(0, 0, 0);
        else
            return new ArmorProperties(priority, ratio, protection);
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        //System.err.println(player.capabilities.allowFlying);
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;

        if (itemStack.getItem() == QAItems.RIFT_BOOTS.getItem())
            player.fallDistance = 0;

        if (itemStack.getItem() == QAItems.RIFT_HELM.getItem())
            player.setAir(300);

        if (itemStack.getItem() == QAItems.RIFT_CHEST.getItem()) {
            if (world.isRemote)
                FlightListener.resetCounter();

            double s = 2.5;
            List<Entity> projectiles = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - s, posY - s, posZ - s, posX + s, posY + s, posZ + s));
            for (Entity ent : projectiles) {
                boolean canThrowable = ent instanceof EntityThrowable && ((EntityThrowable) ent).getThrower() != player;
                boolean canArrow = ent instanceof EntityArrow && ((EntityArrow) ent).shootingEntity != player;
                if (canArrow || canThrowable) {
                    if (world.isRemote) {
                        int loop = RandomUtils.nextInt(15, 30);
                        for (int i = 0; i < loop; i++) {
                            double mx = ent.motionX / 15 + RandomUtils.nextDouble(0, 0.025);
                            double my = ent.motionY / 15 + RandomUtils.nextDouble(0, 0.025);
                            double mz = ent.motionZ / 15 + RandomUtils.nextDouble(0, 0.025);

                            world.spawnParticle("portal", ent.posX + (mx*i), ent.posY - 1 + (my*i), ent.posZ + (mz*i), mx, my, mz);
                        }
                    }
                        //NetworkHandler.wrapper.sendToAllAround(new MessageVanillaParticle("flame", ent.posX, ent.posY, ent.posZ, 0D, 0D, 0D, 2), new NetworkRegistry.TargetPoint(world.provider.dimensionId, posX, posY, posZ, 32));
                    ent.setDead();
                }
            }
        }
    }

    public int handleSource(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source == DamageSource.fall && slot == 0)
            return Integer.MAX_VALUE;

        if (source == DamageSource.fall)
            return 0;

        int protection = 20;

        if (slot == 3)      //Helm
            protection = 120;
        else if (slot == 2) //Chest
            protection = 175;
        else if (slot == 1) //Legs
            protection = 150;
        else if (slot == 0) //Boots
            protection = 100;

        return protection;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        if (slot == HELM)
            return 6;
        else if (slot == CHEST)
            return 10;
        else if (slot == LEGS)
            return 9;
        else if (slot == BOOTS)
            return 5;
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
    }
}
