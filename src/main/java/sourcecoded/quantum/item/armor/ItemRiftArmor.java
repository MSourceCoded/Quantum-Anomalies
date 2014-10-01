package sourcecoded.quantum.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.vacuum.recipes.VacuumArmorBoots;
import sourcecoded.quantum.vacuum.recipes.VacuumArmorChest;
import sourcecoded.quantum.vacuum.recipes.VacuumArmorHead;
import sourcecoded.quantum.vacuum.recipes.VacuumArmorLegs;

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
                VacuumRegistry.addRecipe(new VacuumArmorHead());
                break;
            case CHEST:
                name = "chest";
                VacuumRegistry.addRecipe(new VacuumArmorChest());
                break;
            case LEGS:
                VacuumRegistry.addRecipe(new VacuumArmorLegs());
                name = "legs";
                break;
            case BOOTS:
                VacuumRegistry.addRecipe(new VacuumArmorBoots());
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

    @SuppressWarnings("unchecked")
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        double posX = player.posX;
        double posY = player.posY;
        double posZ = player.posZ;

        if (itemStack.getItem() == QAItems.RIFT_HELM.getItem())
            player.setAir(300);

        if (itemStack.getItem() == QAItems.RIFT_CHEST.getItem()) {
            double s = 2.5;
            List<Entity> projectiles = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - s, posY - s, posZ - s, posX + s, posY + s, posZ + s));

            for (Entity ent : projectiles) {
                boolean canThrowable = ent instanceof EntityThrowable && ((EntityThrowable) ent).getThrower() != player;
                boolean canArrow = ent instanceof EntityArrow && ((EntityArrow) ent).shootingEntity != player;
                if (canArrow || canThrowable) {
                    if (world.isRemote) {
                        int loop = RandomUtils.nextInt(15, 25);
                        for (int i = 0; i < loop; i++) {
                            double mx = ent.motionX / 10 + RandomUtils.nextDouble(0, 0.025);
                            double my = ent.motionY / 10 + RandomUtils.nextDouble(0, 0.025);
                            double mz = ent.motionZ / 10 + RandomUtils.nextDouble(0, 0.025);

                            world.spawnParticle("portal", ent.posX + (mx*i - 1), ent.posY - 1 + (my*i), ent.posZ + (mz*i - 1), mx, my, mz);
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

        if (source == DamageSource.starve)
            return 0;

        int protection = 20;

        if (slot == 3)      //Helm
            protection = 130;
        else if (slot == 2) //Chest
            protection = 200;
        else if (slot == 1) //Legs
            protection = 170;
        else if (slot == 0) //Boots
            protection = 110;

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
