package sourcecoded.quantum.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sourcecoded.quantum.registry.QABlocks;

public class EntityItemJewel extends EntityItem {

    public EntityItemJewel(World world) {
        super(world);
    }

    public EntityItemJewel(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    public EntityItemJewel(World world, Entity tracker, ItemStack stack) {
        this(world, tracker.posX, tracker.posY, tracker.posZ, stack);
        this.motionX = tracker.motionX;
        this.motionY = tracker.motionY;
        this.motionZ = tracker.motionZ;
        this.delayBeforeCanPickup = ((EntityItem) tracker).delayBeforeCanPickup;
    }

    public void setFire(int burnTime) {
        if (!worldObj.isRemote) {
            this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, posX, posY, posZ));
            worldObj.setBlock((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), QABlocks.RIFT_NODE.getBlock());
        }
        this.setDead();
    }
}
