package sourcecoded.quantum.api.gravity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

/**
 * Used for gravity calculations in a tile entity.
 * <p/>
 * Prepare for this class to change throughout updates, as formulae may change/be depreciated
 *
 * @author SourceCoded
 */
public class GravityHandler {

    /**
     * Y-Intercept (b)
     */
    float maxForce;

    /**
     * Gradient (negative) (m)
     */
    float falloffGradient = 0.0125F;

    public GravityHandler(float force) {
        maxForce = force;
    }

    /**
     * Call this on the TileEntity update, it should do just about everything for you
     */
    public void onUpdate(TileEntity tile) {
        World world = tile.getWorldObj();

        float[] min = new float[]{tile.xCoord - getMaxRange(), tile.yCoord - getMaxRange(), tile.zCoord - getMaxRange()};
        float[] max = new float[]{tile.xCoord + getMaxRange(), tile.yCoord + getMaxRange(), tile.zCoord + getMaxRange()};

        if (!world.isRemote) {
            List entities = world.getEntitiesWithinAABB(IGravityEntity.class, AxisAlignedBB.getBoundingBox(min[0], min[1], min[2], max[0], max[1], max[2]));


            for (Object currEnt : entities) {
                Entity entity = (Entity) currEnt;
                //Vec3 direction = world.getWorldVec3Pool().getVecFromPool(entity.posX - tile.xCoord, entity.posY - tile.yCoord, entity.posZ - tile.zCoord);                1.7.2
                Vec3 direction = Vec3.createVectorHelper(entity.posX - tile.xCoord, entity.posY - tile.yCoord, entity.posZ - tile.zCoord);                                  //1.7.10

                final double distToOrigin = direction.lengthVector();

                double force = getAttraction((float) distToOrigin);

                if (force <= 0) continue;
                Vec3 normal = direction.normalize();

                if (entity.motionX < force)
                    entity.motionX -= force * normal.xCoord;
                if (entity.motionY < force)
                    entity.motionY -= force * normal.yCoord;
                if (entity.motionZ < force)
                    entity.motionZ -= force * normal.zCoord;

                ((IGravityEntity) entity).onGravityAffected((float) force);
            }
        }
    }

    /**
     * Set the maximum amount of force it can provide
     */
    public void setMaxForce(float max) {
        this.maxForce = max;
    }

    /**
     * Get the maximum amount of force it can provide
     */
    public float getMaxForce() {
        return maxForce;
    }

    /**
     * Get the amount of attraction from the distance specified
     */
    public float getAttraction(float distance) {
        //y = mx + b
        return maxForce - (falloffGradient * distance);
    }

    /**
     * The maximum range this object can attract from
     */
    public float getMaxRange() {
        return (float) maxForce / falloffGradient;
    }

    /**
     * Can I be attracted from this distance?
     */
    public boolean canAttract(float distance) {
        return distance <= getMaxRange();
    }
}
