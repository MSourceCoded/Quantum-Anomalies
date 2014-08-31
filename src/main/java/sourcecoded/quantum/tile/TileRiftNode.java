package sourcecoded.quantum.tile;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.utils.WorldUtils;

import java.util.List;

public class TileRiftNode extends TileQuantum implements ITileRiftHandler {

    public RiftEnergyStorage riftStorage = new RiftEnergyStorage(10000);

    transient int ticker;
    transient int radius = 10;
    transient int maxPacket = 100;
    transient int boltValueMin = 3000;
    transient int boltValueMax = 7000;

    public transient int maxShockCooldown = 100;
    public int shockCooldown = 0;

    float force = 0.1F;

    @SuppressWarnings("unchecked")
    public void updateEntity() {
        super.updateEntity();
        ticker++;

        if (shockCooldown > 0)
            shockCooldown--;

        if (this.worldObj.isRemote) {
            //FXManager.portalFX1Fragment(worldObj, xCoord, yCoord, zCoord);
            if (this.worldObj.rand.nextInt(9) == 0) {
                FXManager.riftNodeFX1Larger(0.7F, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                //FXManager.portalFX1Hole(2F, worldObj, xCoord, yCoord, zCoord);
                //FXManager.portalFX2Filler(2F, worldObj, xCoord, yCoord, zCoord);

            }
            if (this.worldObj.rand.nextInt(5) == 0) {
                FXManager.riftNodeFX1Smaller(0.7F, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                FXManager.orbitingFX1(0.1F, worldObj, xCoord, yCoord, zCoord);
            }
        } else {
            if (ticker % 5 == 0) checkLightning();
            if (ticker >= 20) {
                ticker = 0;

                force = 0.1F;
                List<TileEntity> tiles = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, radius, radius, radius, ITileRiftHandler.class);

                for (TileEntity tile : tiles) {
                    ITileRiftHandler handler = (ITileRiftHandler) tile;
                    int remaining = handler.getMaxRiftEnergy() - handler.getRiftEnergy();
                    int toSend = Math.min(remaining, maxPacket);

                    boolean canSend = (handler.getRiftEnergy() + toSend) <= handler.getMaxRiftEnergy();

                    if (toSend > 0 && riftStorage.getRiftEnergy() >= toSend && canSend) {

                        if (handler.getBehaviour() == EnergyBehaviour.NOT_ACCEPTING) continue;
                        if (handler.getBehaviour() == EnergyBehaviour.EQUALIZE)
                            if ((handler.getRiftEnergy() + toSend) >= getRiftEnergy()) continue;

                        EntityEnergyPacket entity = new EntityEnergyPacket(worldObj, toSend, xCoord, yCoord, zCoord);
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

                    }
                }
                markDirty();
            }
        }
    }

    public void checkLightning() {
        List bolts = worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, AxisAlignedBB.getBoundingBox(xCoord - radius, 0, zCoord - radius, xCoord + radius, 256, zCoord + radius));
        if (shockCooldown == 0)
            giveRiftEnergy(RandomUtils.nextInt(boltValueMin, boltValueMax) * bolts.size());
        if (bolts.size() > 0)
            shockCooldown = maxShockCooldown;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        riftStorage.writeRiftToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        riftStorage.readRiftFromNBT(nbt);
    }

    @Override
    public int takeRiftEnergy(int amount) {
        return riftStorage.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
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
