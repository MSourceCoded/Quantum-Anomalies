package sourcecoded.quantum.tile;

import net.minecraft.nbt.NBTTagCompound;
import sourcecoded.quantum.api.block.Colourizer;

public class TileCornerstone extends TileQuantum implements IDyeable {

    public Colourizer colour = Colourizer.PURPLE;

    @Override
    public void dye(Colourizer colour) {
        if (!worldObj.isRemote) {
            this.colour = colour;
            update();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tags) {
        super.writeToNBT(tags);

        tags.setInteger("colourIndex", colour.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tags) {
        super.readFromNBT(tags);

        colour = Colourizer.values()[tags.getInteger("colourIndex")];
    }

    void update() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
