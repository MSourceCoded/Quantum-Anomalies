package sourcecoded.quantum.api.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Used as a container for TileEntities and Items
 * that takes advantage of IRiftEnergyStorage.
 * This is the recommended implementation
 *
 * @see sourcecoded.quantum.api.energy.IRiftEnergyStorage
 * @see sourcecoded.quantum.api.energy.ITileRiftHandler
 * @see sourcecoded.quantum.api.energy.IItemRiftHandler
 *
 * @author SourceCoded
 */
public class RiftEnergyStorage implements IRiftEnergyStorage {

    public int riftAmount;
    public int riftCapacity;

    /**
     * Create a new Rift storage with the
     * given capacity
     */
    public RiftEnergyStorage(int capacity) {
        riftCapacity = capacity;
    }

    /**
     * Give the storage energy.
     * @param amount The amount of energy
     *               to be given
     * @return The amount of energy that
     * was accepted
     */
    @Override
    public int giveRiftEnergy(int amount) {
        int amountGiven = Math.min(riftCapacity - riftAmount, amount);

        riftAmount += amountGiven;

        return amountGiven;
    }

    /**
     * Take energy from the storage
     *
     * @param amount The amount of energy
     *               to be taken
     * @return The amount of energy
     * that was taken from the storage
     */
    @Override
    public int takeRiftEnergy(int amount) {
        int amountTaken = Math.min(riftAmount, amount);

        riftAmount -= amountTaken;

        return amountTaken;
    }

    /**
     * Get the current amount of Rift Energy
     * in the storage
     */
    @Override
    public int getRiftEnergy() {
        return riftAmount;
    }

    /**
     * Get the Maximum capacity of the
     * storage
     */
    @Override
    public int getMaxRiftEnergy() {
        return riftCapacity;
    }

    /**
     * Write this rift storage to an
     * NBTCompound
     */
    public void writeRiftToNBT(NBTTagCompound tag) {
        NBTTagCompound riftContainer = new NBTTagCompound();
        riftContainer.setInteger("riftEnergy", riftAmount);
        riftContainer.setInteger("riftCapacity", riftCapacity);

        tag.setTag("Rift", riftContainer);
    }

    /**
     * Read this rift storage from an
     * NBTCompound
     */
    public void readRiftFromNBT(NBTTagCompound tag) {
        NBTTagCompound riftContainer = tag.getCompoundTag("Rift");
        this.riftAmount = riftContainer.getInteger("riftEnergy");
        this.riftCapacity = riftContainer.getInteger("riftCapacity");
    }
}
