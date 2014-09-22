package sourcecoded.quantum.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.block.Colourizer;

import java.util.ArrayList;
import java.util.List;

public class TileCornerstone extends TileDyeable {

    public List<IInventory> getAdjacentInventories() {
        List<IInventory> inventories = new ArrayList<IInventory>();
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile != null && tile instanceof IInventory)
                inventories.add((IInventory) tile);
        }
        return inventories;
    }

}
