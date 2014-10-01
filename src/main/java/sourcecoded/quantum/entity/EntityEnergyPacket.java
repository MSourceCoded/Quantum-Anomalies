package sourcecoded.quantum.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.gravity.IGravityEntity;

import java.util.List;

/**
 * Please move me to an EntityFX class. PLS.
 */
public class EntityEnergyPacket extends Entity implements IGravityEntity {

    /**
     * The age of the Entity in ticks
     */
    public int age;

    /**
     * The maximum age before the entity will die
     * <p/>
     * //RIP
     */
    public int maxAge = 400;

    public int[] origin;

    /**
     * The energy stored in this entity
     */
    public int energy;

    Colourizer colour = Colourizer.PURPLE;

    public EntityEnergyPacket(World world) {
        super(world);
        this.noClip = true;
        this.setSize(0.1F, 0.1F);
    }

    public EntityEnergyPacket(World world, int energy, int xOrigin, int yOrigin, int zOrigin, Colourizer colour) {
        this(world);
        origin = new int[3];
        origin[0] = xOrigin;
        origin[1] = yOrigin;
        origin[2] = zOrigin;
        this.energy = energy;
        this.colour = colour;
        dataWatcherInit();
    }

    public void setEnergy(int amount) {
        this.energy = amount;
    }

    public int getEnergy() {
        return this.dataWatcher.getWatchableObjectInt(10);
    }

    public Colourizer getColour() {
        return Colourizer.values()[this.dataWatcher.getWatchableObjectInt(11)];
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    void dataWatcherInit() {
        this.dataWatcher.updateObject(10, energy);
        this.dataWatcher.updateObject(11, colour.ordinal());
    }

    @Override
    public void entityInit() {
        this.dataWatcher.addObject(10, energy);
        this.dataWatcher.addObject(11, Colourizer.PURPLE.ordinal());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdate() {
        super.onUpdate();

        age++;
        if (age >= maxAge)
            this.setDead();

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition mop = this.worldObj.rayTraceBlocks(vec3, vec31);

        if (mop != null) {
            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (origin != null && (!(origin[0] == mop.blockX && origin[1] == mop.blockY && origin[2] == mop.blockZ))) {
                    TileEntity tile = worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);

                    if (tile != null && tile instanceof ITileRiftHandler) {
                        this.energy -= ((ITileRiftHandler) tile).giveRiftEnergy(energy);
                        dataWatcher.updateObject(10, energy);
                        if (energy <= 0)
                            this.setDead();
                    }
                }
            }
        }

        if (worldObj.rand.nextInt(10) == 0) {
            List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX - 0.5, this.posY - 0.5, this.posZ - 0.5, this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5));
            for (EntityLivingBase ent : list) {
                Potion[] effects = Potion.potionTypes;
                int select = RandomUtils.nextInt(0, effects.length);
                try {
                    if (!worldObj.isRemote)
                        ((EntityLivingBase) ent).addPotionEffect(new PotionEffect(effects[select].getId(), RandomUtils.nextInt(60, 200), 0, true));
                } catch (NullPointerException e) {} //Potion effect shouldn't exist
                this.setDead();
            }
        }

        this.moveEntity(motionX, motionY, motionZ);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d1 = 0.25D;
        d1 *= 64.0D * this.renderDistanceWeight;
        return distance < d1 * d1;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        energy = nbt.getInteger("energy");
        colour = Colourizer.values()[nbt.getInteger("colourIndex")];
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("energy", energy);
        nbt.setInteger("colourIndex", colour.ordinal());
    }

    @Override
    public void onGravityAffected(float force) {
    }
}
