package sourcecoded.quantum.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArmorRevenge extends EntityAmbientCreature {

    //TODO this

    public EntityArmorRevenge(World theWorld) {
        super(theWorld);
        setSize(1F, 1F);
    }

    protected void fall(float distance) {
    }

    protected void updateFallState(double fallForCurrentTick, boolean landed) {
    }

    public boolean isOnLadder() {                                           //Prevents slowdown in ladder blocks
        return false;
    }

    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {         //Flight mechanics
        if (isInWater()) {
            moveFlying(strafe, forward, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.8D;
            motionY *= 0.8D;
            motionZ *= 0.8D;
        } else if (handleLavaMovement()) {
            moveFlying(strafe, forward, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        } else {
            float speed = 0.91F;

            if (onGround)  {
                speed = 0.5F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                speed = block.slipperiness * 0.91F;
            }

            float f3 = 0.16F / (speed * speed * speed);
            moveFlying(strafe, forward, onGround ? 0.1F * f3 : 0.02F);
            speed = 0.91F;

            if (onGround) {
                speed = 0.54600006F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                speed = block.slipperiness * 0.91F;
            }

            moveEntity(motionX, motionY, motionZ);
            motionX *= speed;
            motionY *= speed;
            motionZ *= speed;
        }
    }

}
