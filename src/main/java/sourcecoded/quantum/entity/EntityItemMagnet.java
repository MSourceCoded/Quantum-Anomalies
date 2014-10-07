package sourcecoded.quantum.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemMagnet extends EntityItem {

    public EntityItemMagnet(World world) {
        super(world);
    }

    public EntityItemMagnet(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    public EntityItemMagnet(World world, Entity tracker, ItemStack stack) {
        this(world, tracker.posX, tracker.posY, tracker.posZ, stack);
        this.motionX = tracker.motionX;
        this.motionY = tracker.motionY;
        this.motionZ = tracker.motionZ;
        this.delayBeforeCanPickup = ((EntityItem) tracker).delayBeforeCanPickup;
    }

    public boolean handleWaterMovement() {
        this.getEntityItem().stackTagCompound.removeTag("blacklist");
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }
}
