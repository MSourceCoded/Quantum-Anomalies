package sourcecoded.quantum.api.energy;

import net.minecraft.nbt.NBTTagCompound;

public class RiftEnergyStorage implements IRiftEnergyStorage {

    public int riftAmount;
    public int riftCapacity;

    public RiftEnergyStorage(int capacity) {
        riftCapacity = capacity;
    }

    @Override
    public int giveRiftEnergy(int amount) {
        int amountGiven = Math.min(riftCapacity - riftAmount, amount);

        riftAmount += amountGiven;

        return amountGiven;
    }

    @Override
    public int takeRiftEnergy(int amount) {
        int amountTaken = Math.min(riftAmount, amount);

        riftAmount -= amountTaken;

        return amountTaken;
    }

    @Override
    public int getRiftEnergy() {
        return riftAmount;
    }

    @Override
    public int getMaxRiftEnergy() {
        return riftCapacity;
    }

    public void writeRiftToNBT(NBTTagCompound tag) {
        NBTTagCompound riftContainer = new NBTTagCompound();
        riftContainer.setInteger("riftEnergy", riftAmount);
        riftContainer.setInteger("riftCapacity", riftCapacity);

        tag.setTag("Rift", riftContainer);
    }

    public void readRiftFromNBT(NBTTagCompound tag) {
        NBTTagCompound riftContainer = tag.getCompoundTag("Rift");
        this.riftAmount = riftContainer.getInteger("riftEnergy");
        this.riftCapacity = riftContainer.getInteger("riftCapacity");
    }
}
