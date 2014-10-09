package sourcecoded.quantum.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class RayTracing {

    /**
     * Ray trace a block with the given Entity's look Vector
     * and reach distance.
     */
    public static MovingObjectPosition rayTrace(EntityLivingBase tracer, double reach, float par1) {
        Vec3 positionVector = tracer.getPosition(par1);
        if (tracer.getEyeHeight() != 0.12F)
            positionVector.yCoord += tracer.getEyeHeight();

        Vec3 lookVector = tracer.getLook(par1);
        Vec3 traceVector = positionVector.addVector(lookVector.xCoord * reach, lookVector.yCoord * reach, lookVector.zCoord * reach);

        return tracer.worldObj.rayTraceBlocks(positionVector, traceVector, true);
    }

    public static MovingObjectPosition rayTrace(EntityLivingBase player) {
        return rayTrace(player, getPlayerReach(player), 0);
    }

    public static double getPlayerReach(EntityLivingBase player) {
        return ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
    }

}
