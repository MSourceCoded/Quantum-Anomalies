package sourcecoded.quantum.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TilePlayer extends TileDyeable implements ISidedInventory {

    public String owner;

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString("ownerUUID", owner);
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        owner = compound.getString("ownerUUID");
    }

    public void setOwner(String UUID) {
        owner = UUID;
    }

    public EntityPlayer getPlayer() {
        for (Object player : worldObj.playerEntities) {
            if (player instanceof EntityPlayer)
                if (((EntityPlayer) player).getUniqueID().toString().equals(owner)) return (EntityPlayer) player;
        }

        return null;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        EntityPlayer player = getPlayer();
        if (player != null) {
            int[] intarray = new int[player.inventory.getSizeInventory()];
            for (int i = 0; i < intarray.length; i++)
                intarray[i] = i;

            return intarray;
        }
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public int getSizeInventory() {
        return getPlayer().inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return getPlayer().inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return getPlayer().inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return getPlayer().inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        getPlayer().inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName() {
        EntityPlayer player = getPlayer();
        return player != null ? player.getDisplayName() : "Player Entanglement Interface";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        EntityPlayer player = getPlayer();
        return player != null && player.inventory.isItemValidForSlot(slot, stack);
    }
}
