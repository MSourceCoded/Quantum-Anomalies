package sourcecoded.quantum.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
    public void updateEntity() {
        if (getColour() == Colourizer.BLUE)
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile != null) {
                if (tile instanceof IFluidHandler) {
                    IFluidHandler handler = (IFluidHandler) tile;
                    if (handler.canFill(dir.getOpposite(), FluidRegistry.WATER))
                        handler.fill(dir.getOpposite(), new FluidStack(FluidRegistry.WATER, 50), true);
                } else if (tile instanceof IFluidTank) {        //WHY ARE THESE NOT 1 CLASS 3:<
                    IFluidTank tank = (IFluidTank) tile;
                    tank.fill(new FluidStack(FluidRegistry.WATER, 50), true);
                }
            }
        }
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
        return new FluidStack(FluidRegistry.WATER, resource.amount);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return new FluidStack(FluidRegistry.WATER, maxDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return getColour() == Colourizer.BLACK;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return getColour() == Colourizer.BLUE && fluid == FluidRegistry.WATER;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (getColour() == Colourizer.BLACK)
            return new FluidTankInfo[] {new FluidTankInfo(null, Integer.MAX_VALUE)};
        else if (getColour() == Colourizer.BLUE)
            return new FluidTankInfo[] {new FluidTankInfo(new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE), Integer.MAX_VALUE)};
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
