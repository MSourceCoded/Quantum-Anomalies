package sourcecoded.quantum.tile;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.network.MessageVanillaParticle;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.utils.WorldUtils;

import java.util.List;

public class TileRiftNode extends TileQuantum implements ITileRiftHandler, IDyeable {

    public RiftEnergyStorage riftStorage = new RiftEnergyStorage(10000);

    transient int ticker;
    transient int ticker2;
    transient int radius = 10;
    transient int maxPacket = 100;
    transient int boltValueMin = 3000;
    transient int boltValueMax = 7000;

    transient int fireVal = 2;

    public transient int maxShockCooldown = 100;
    public int shockCooldown = 0;

    float force = 0.1F;

    public Colourizer colour = Colourizer.PURPLE;

    @Override
    public void dye(Colourizer colour) {
        this.colour = colour;
        update();
    }

    @Override
    public Colourizer getColour() {
        return colour;
    }

    @SuppressWarnings("unchecked")
    public void updateEntity() {
        super.updateEntity();
        ticker++;
        ticker2++;

        if (shockCooldown > 0)
            shockCooldown--;

        if (this.worldObj.isRemote) {
            //FXManager.portalFX1Fragment(worldObj, xCoord, yCoord, zCoord);
            float size = Math.max(1F * ((float) getRiftEnergy() / (float) getMaxRiftEnergy()), 0.1F);
            if (this.worldObj.rand.nextInt(9) == 0) {
                FXManager.riftNodeFX1Larger(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, colour);
                //FXManager.portalFX1Hole(2F, worldObj, xCoord, yCoord, zCoord);
                //FXManager.portalFX2Filler(2F, worldObj, xCoord, yCoord, zCoord);
            }
            if (this.worldObj.rand.nextInt(5) == 0) {
                FXManager.riftNodeFX1Smaller(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                if (size > 0.4F)
                    FXManager.orbitingFX1(size / 10F, worldObj, xCoord, yCoord, zCoord, colour);
            }
        } else {
            if (ticker2 % 5 == 0)
                checkLightning();

            if (ticker2 % 40 == 0)
                checkFire();

            if (ticker >= 30) {
                ticker = 0;

                force = 0.1F;
                List<TileEntity> tiles = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, radius, radius, radius, ITileRiftHandler.class);

                for (TileEntity tile : tiles) {
                    if (tile instanceof IDyeable) {
                        Colourizer c = ((IDyeable) tile).getColour();
                        if (c != Colourizer.PURPLE && colour != Colourizer.PURPLE && c != colour) continue;
                    }

                    ITileRiftHandler handler = (ITileRiftHandler) tile;
                    int remaining = handler.getMaxRiftEnergy() - handler.getRiftEnergy();
                    int toSend = Math.min(remaining, maxPacket);

                    boolean canSend = (handler.getRiftEnergy() + toSend) <= handler.getMaxRiftEnergy();

                    if (toSend > 0 && riftStorage.getRiftEnergy() >= toSend && canSend) {

                        if (handler.getBehaviour() == EnergyBehaviour.NOT_ACCEPTING) continue;
                        if (handler.getBehaviour() == EnergyBehaviour.EQUALIZE)
                            if ((handler.getRiftEnergy() + toSend) >= getRiftEnergy()) continue;

                        EntityEnergyPacket entity = new EntityEnergyPacket(worldObj, toSend, xCoord, yCoord, zCoord, colour);
                        entity.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);

                        worldObj.spawnEntityInWorld(entity);

                        riftStorage.takeRiftEnergy(toSend);

                        Vec3 direction = Vec3.createVectorHelper(tile.xCoord - xCoord, tile.yCoord - yCoord, tile.zCoord - zCoord);
                        Vec3 normal = direction.normalize();

                        if (entity.motionX < force)
                            entity.motionX += force * normal.xCoord;
                        if (entity.motionY < force)
                            entity.motionY += force * normal.yCoord;
                        if (entity.motionZ < force)
                            entity.motionZ += force * normal.zCoord;

                        update();
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void checkLightning() {
        if (getColour() != Colourizer.LIGHT_BLUE) return;
        List bolts = worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, AxisAlignedBB.getBoundingBox(xCoord - radius, 0, zCoord - radius, xCoord + radius, 256, zCoord + radius));

        for (Object entity : worldObj.weatherEffects) {
            if (entity instanceof EntityLightningBolt)                  //Don't forget to check the locale later TODO
                bolts.add(entity);
        }

        if (shockCooldown == 0)
            giveRiftEnergy(RandomUtils.nextInt(boltValueMin, boltValueMax) * bolts.size());
        if (bolts.size() > 0) {
            shockCooldown = maxShockCooldown;
            update();
        }
    }

    public void checkFire() {
        if (getColour() != Colourizer.RED) return;

        boolean didThing = false;

        List list = WorldUtils.searchForBlock(worldObj, xCoord, yCoord, zCoord, radius, radius, radius, BlockFire.class);
        for (Object block : list)
            if (block instanceof BlockFire) {
                if (giveRiftEnergy(fireVal) != 0)
                    didThing = true;
            }

        if (didThing) {
            IMessage message = new MessageVanillaParticle("lava", xCoord + 0.5 , yCoord + 0.6, zCoord + 0.5, 0D, 0.2D, 0D);
            NetworkHandler.wrapper.sendToDimension(message, worldObj.provider.dimensionId);
        }

        update();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        riftStorage.writeRiftToNBT(nbt);
        nbt.setInteger("colourIndex", colour.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        riftStorage.readRiftFromNBT(nbt);
        colour = Colourizer.values()[nbt.getInteger("colourIndex")];
    }

    @Override
    public int takeRiftEnergy(int amount) {
        update();
        return riftStorage.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        update();
        return riftStorage.giveRiftEnergy(amount);
    }

    @Override
    public int getRiftEnergy() {
        return riftStorage.getRiftEnergy();
    }

    @Override
    public int getMaxRiftEnergy() {
        return riftStorage.getMaxRiftEnergy();
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return EnergyBehaviour.EQUALIZE;
    }
}
