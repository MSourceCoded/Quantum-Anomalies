package sourcecoded.quantum.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;

public class TileManipulation extends TileDyeable implements IFluidHandler, ITileRiftHandler {

    public RiftEnergyStorage storage;

    public TileManipulation() {
        storage = new RiftEnergyStorage(10000);
    }

    @Override
    public void writeToNBT(NBTTagCompound tags) {
        super.writeToNBT(tags);

        storage.writeRiftToNBT(tags);
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        super.readFromNBT(tags);

        storage.readRiftFromNBT(tags);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (getColour() != Colourizer.BLACK)
            return 0;
        else
            return resource.amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return getColour() == Colourizer.BLACK;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return null;
    }

    @Override
    public int takeRiftEnergy(int amount) {
        return storage.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        return storage.giveRiftEnergy(amount);
    }

    @Override
    public int getRiftEnergy() {
        return storage.getRiftEnergy();
    }

    @Override
    public int getMaxRiftEnergy() {
        return storage.getMaxRiftEnergy();
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return EnergyBehaviour.DRAIN;
    }
}
