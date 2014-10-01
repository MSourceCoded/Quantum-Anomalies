package sourcecoded.quantum.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sourcecoded.quantum.api.block.IRiftMultiplier;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.utils.MathUtils;
import sourcecoded.quantum.utils.WorldUtils;

public class TileRiftSmelter extends TileDyeable implements ISidedInventory, ITileRiftHandler {

    /** Items to cook */
    private static final int[] slotsTop = new int[]{0};

    /** Extract Cooked Items */
    private static final int[] slotsSides = new int[]{1};
    private static final int[] slotsBottom = new int[]{1};

    /** The inventory slots of the tile */
    private ItemStack[] itemSlots = new ItemStack[2];

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;

    /** The Energy per item smelted */
    public int energyPerOperation = 160;

    /** The amount of time it takes to cook an item */
    public int cookTime = 30;

    public int ticker = 0;

    public float speed = 1F;
    public float energy = 1F;
    public float production = 1F;

    //Rift Storage
    RiftEnergyStorage rift;

    public TileRiftSmelter() {
        rift = new RiftEnergyStorage(50000);
    }

    public void updateEntity() {
        boolean update = false;

        if (!this.worldObj.isRemote) {
            ticker++;
            if (this.itemSlots[0] != null) {
                if (this.canSmelt()) {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime >= (speed * cookTime)) {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        update = true;
                    }
                } else
                    this.furnaceCookTime = 0;
            }

            if (ticker >= 10) {
                speed = WorldUtils.getMultiplication(worldObj, xCoord, yCoord, zCoord, 5, 5, 5, IRiftMultiplier.RiftMultiplierTypes.SPEED);
                energy = WorldUtils.getMultiplication(worldObj, xCoord, yCoord, zCoord, 5, 5, 5, IRiftMultiplier.RiftMultiplierTypes.ENERGY_USAGE);
                production = WorldUtils.getMultiplication(worldObj, xCoord, yCoord, zCoord, 5, 5, 5, IRiftMultiplier.RiftMultiplierTypes.PRODUCTION);
                ticker = 0;
            }
        }

        if (update)
            update();
    }

    private boolean canSmelt() {
        if (getRiftEnergy() < energyPerOperation) return false;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) return false;

        if (this.itemSlots[0] == null)
            return false;
        else {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemSlots[0]);
            if (itemstack == null) return false;
            if (!multiblock()) return false;
            if (this.itemSlots[1] == null) return true;
            if (!this.itemSlots[1].isItemEqual(itemstack)) return false;
            int result = itemSlots[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.itemSlots[1].getMaxStackSize();
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemSlots[0]);

            itemstack.stackSize = MathUtils.getProductionAmount(1, production);

            if (this.itemSlots[1] == null)
                this.itemSlots[1] = itemstack.copy();

            else if (this.itemSlots[1].getItem() == itemstack.getItem())
                this.itemSlots[1].stackSize += itemstack.stackSize;

            --this.itemSlots[0].stackSize;
            takeRiftEnergy((int) ((float) energyPerOperation * energy));

            if (this.itemSlots[0].stackSize <= 0)
                this.itemSlots[0] = null;
        }
    }

    public boolean checkSmeltable(ItemStack item) {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(item);
        return itemstack != null;
    }

    //Oh god why
    public boolean multiblock() {
        if (worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord + 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord - 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord + 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord - 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;

        if (worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord + 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord - 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord + 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord - 1) != QABlocks.INJECTED_CORNERSTONE.getBlock()) return false;

        if (worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord + 1, yCoord - 1, zCoord) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord - 1, zCoord) != QABlocks.INJECTED_STONE.getBlock()) return false;

        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord, yCoord - 1, zCoord + 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord, yCoord - 1, zCoord - 1) != QABlocks.INJECTED_STONE.getBlock()) return false;

        if (worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1) != QABlocks.INJECTED_STONE.getBlock()) return false;
        if (worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1) != QABlocks.INJECTED_STONE.getBlock()) return false;

        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        rift.writeRiftToNBT(nbt);

        nbt.setShort("CookTime", (short) this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.itemSlots.length; ++i) {
            if (this.itemSlots[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.itemSlots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        rift.readRiftFromNBT(nbt);

        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.itemSlots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemSlots.length)
                this.itemSlots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
        }

        this.furnaceCookTime = nbt.getShort("CookTime");
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return this.isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot != 0;
    }

    @Override
    public int getSizeInventory() {
        return itemSlots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemSlots[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.itemSlots[slot] != null) {
            ItemStack itemstack;

            if (this.itemSlots[slot].stackSize <= amount) {
                itemstack = this.itemSlots[slot];
                this.itemSlots[slot] = null;
                return itemstack;
            } else {
                itemstack = this.itemSlots[slot].splitStack(amount);

                if (this.itemSlots[slot].stackSize == 0)
                    this.itemSlots[slot] = null;

                return itemstack;
            }
        } else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.itemSlots[slot] != null) {
            ItemStack itemstack = this.itemSlots[slot];
            this.itemSlots[slot] = null;
            return itemstack;
        } else
            return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.itemSlots[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "Rift Smelter";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
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
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot != 2 && multiblock() && checkSmeltable(item);
    }

    @Override
    public int takeRiftEnergy(int amount) {
        return rift.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        return rift.giveRiftEnergy(amount);
    }

    @Override
    public int getRiftEnergy() {
        return rift.getRiftEnergy();
    }

    @Override
    public int getMaxRiftEnergy() {
        return rift.getMaxRiftEnergy();
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return multiblock() ? EnergyBehaviour.DRAIN : EnergyBehaviour.NOT_ACCEPTING;
    }
}
