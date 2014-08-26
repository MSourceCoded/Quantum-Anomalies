package sourcecoded.quantum.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;

public class TileDebug extends TileEntity implements ITileRiftHandler {

    int energy = 0;

    public void updateEntity() {
        super.updateEntity();
        //System.err.println(getRiftEnergy());
    }

    @Override
    public int takeRiftEnergy(int amount) {
        return 0;
    }

    @Override
    public int giveRiftEnergy(int amount) {
        energy += amount;
        return amount;
    }

    @Override
    public int getRiftEnergy() {
        return energy;
    }

    @Override
    public int getMaxRiftEnergy() {
        return 1000;
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return EnergyBehaviour.DRAIN;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("energy", energy);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        energy = nbt.getInteger("energy");
    }
}
