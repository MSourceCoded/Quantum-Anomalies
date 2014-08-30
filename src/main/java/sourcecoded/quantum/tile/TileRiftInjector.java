package sourcecoded.quantum.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.api.block.IRiftMultiplier;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.registry.BlockRegistry;
import sourcecoded.quantum.structure.Multiblock;
import sourcecoded.quantum.utils.WorldUtils;

public class TileRiftInjector extends TileEntity implements ITileRiftHandler, ISidedInventory {

    public RiftEnergyStorage rift = new RiftEnergyStorage(5000);

    public ItemStack currentItem;

    public int ticker = 0;
    public int multiblockTicker = 0;

    public int tier;

    public float speed = 1F;
    public float energy = 1F;

    /* The amount of energy to infuse per tick */
    public int energyPerTick = 5;

    public int energyInfused = 0;

    boolean isParticle = false;

    Multiblock tier1 = new Multiblock("cic", "i i", "cic", 'c', BlockRegistry.instance().getBlockByName("cornerStone"), 'i', BlockRegistry.instance().getBlockByName("infusedStone"));
    Multiblock tier2 = new Multiblock("ciiic", "i   i", "i   i", "i   i", "ciiic", 'c', BlockRegistry.instance().getBlockByName("cornerStone"), 'i', BlockRegistry.instance().getBlockByName("infusedStone"));
    Multiblock[] tier3 = {
            new Multiblock("ciiiiic", "i     i", "i     i", "i     i", "i     i", "i     i", "ciiiiic", 'c', BlockRegistry.instance().getBlockByName("cornerStone"), 'i', BlockRegistry.instance().getBlockByName("infusedStone")),
            new Multiblock("i     i", "       ", "       ", "       ", "       ", "       ", "i     i", 'i', BlockRegistry.instance().getBlockByName("infusedStone")),
            new Multiblock("i     i", "       ", "       ", "       ", "       ", "       ", "i     i", 'i', BlockRegistry.instance().getBlockByName("infusedStone")),
            new Multiblock("i     i", "       ", "       ", "       ", "       ", "       ", "i     i", 'i', BlockRegistry.instance().getBlockByName("infusedStone")),
            new Multiblock("c     c", "       ", "       ", "       ", "       ", "       ", "c     c", 'c', BlockRegistry.instance().getBlockByName("cornerStone")),
    };

    public void updateEntity() {
        if (ticker >= 10) {
            speed = WorldUtils.getMultiplication(worldObj, xCoord, yCoord, zCoord, 5, 5, 5, IRiftMultiplier.RiftMultiplierTypes.SPEED);
            energy = WorldUtils.getMultiplication(worldObj, xCoord, yCoord, zCoord, 5, 5, 5, IRiftMultiplier.RiftMultiplierTypes.ENERGY_USAGE);
            ticker = 0;
        }

        ticker++;

        if (!this.worldObj.isRemote) {
            if (this.currentItem != null) {
                if (this.canInject()) {
                    int energyToTake = (int) Math.floor((float)energyPerTick / (float)speed);
                    if (energyToTake < getRiftEnergy()) {
                        isParticle = true;
                        energyInfused += energyToTake;
                        takeRiftEnergy(energyToTake);
                        IInjectorRecipe recipe = InjectorRegistry.getRecipeForInput(currentItem);
                        if (energyInfused >= (recipe.getEnergyRequired() * currentItem.stackSize)) {
                            inject();
                            energyInfused = 0;
                            update();
                        }
                    } else {
                        isParticle = false;
                        update();
                    }
                } else energyInfused = 0;
            } else energyInfused = 0;

            if (multiblockTicker >= 100) multiblockTicker = 0;
            if (multiblockTicker == 0) tier = getTier();

            if (energyInfused == 0) isParticle = false;

        } else {
            if (isParticle && ticker % 5 == 0) {
                FXManager.injectionFX(0.1F, worldObj, xCoord + 0.5F, yCoord + 0.3F, zCoord + 0.5F, true);
                FXManager.injectionFX(0.1F, worldObj, xCoord + 0.5F, yCoord + 0.3F, zCoord + 0.5F, false);
            }
        }

        multiblockTicker++;

    }

    int getTier() {
        int tier = 0;
        if (tier1.valid(worldObj, xCoord, yCoord - 1, zCoord)) tier = 1;
        else return tier;
        if (tier2.valid(worldObj, xCoord, yCoord - 2, zCoord)) tier = 2;
        else return tier;

        for (int i = 0; i < tier3.length; i++)
            if (!tier3[i].valid(worldObj, xCoord, yCoord - 3 + i, zCoord)) return tier;
        tier++;

        return tier;
    }

    public void click(EntityPlayer clickEntity) {
        if (clickEntity.getCurrentEquippedItem() == null) {
            if (currentItem == null) return;
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1D, zCoord, currentItem));
            currentItem = null;
            update();
        } else {
            ItemStack held = clickEntity.getCurrentEquippedItem();
            ItemStack newHeld = held.copy();

            if (InjectorRegistry.getRecipeForInput(held) == null) return;

            newHeld.stackSize = 1;

            if (currentItem != null && newHeld.isItemEqual(currentItem)) {
                if (currentItem.stackSize == getInventoryStackLimit()) return;
                currentItem.stackSize++;
            }
            if (currentItem == null) {
                currentItem = newHeld;
            }

            held.stackSize--;
            clickEntity.setCurrentItemOrArmor(0, held);

            update();
        }

        System.err.println(getRiftEnergy());
    }

    void inject() {
        if (this.canInject()) {
            IInjectorRecipe recipe = InjectorRegistry.getRecipeForInput(currentItem);
            ItemStack itemstack = recipe.getOutput();

            itemstack.stackSize *= currentItem.stackSize;

            currentItem = itemstack;
        }
    }

    boolean canInject() {
        if (!InjectorRegistry.hasRecipeForInput(currentItem)) return false;
        IInjectorRecipe recipe = InjectorRegistry.getRecipeForInput(currentItem);

        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) return false;

        if (recipe.getTier() > tier) return false;

        if (this.currentItem == null)
            return false;
        else {
            ItemStack itemstack = recipe.getOutput();
            if (itemstack == null) return false;
            int result = currentItem.stackSize * itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.currentItem.getMaxStackSize();
        }
    }

    void update() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        rift.writeRiftToNBT(nbt);

        nbt.setInteger("energyInfused", energyInfused);

        NBTTagList nbttaglist = new NBTTagList();

        if (this.currentItem != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            this.currentItem.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
        }

        nbt.setTag("Items", nbttaglist);

        nbt.setBoolean("isCooking", isParticle);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        rift.readRiftFromNBT(nbt);

        energyInfused = nbt.getInteger("energyInfused");

        NBTTagList nbttaglist = nbt.getTagList("Items", 10);

            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
                this.currentItem = ItemStack.loadItemStackFromNBT(nbttagcompound1);

        isParticle = nbt.getBoolean("isCooking");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
        markDirty();
    }

    @Override
    public int takeRiftEnergy(int amount) {
        update();
        return rift.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        update();
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
        return EnergyBehaviour.DRAIN;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1, 2, 3, 4, 5};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        update();
        return this.isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        update();
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return currentItem;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.currentItem != null) {
            ItemStack itemstack;

            if (this.currentItem.stackSize <= amount) {
                itemstack = this.currentItem;
                this.currentItem = null;
                update();
                return itemstack;
            } else {
                itemstack = this.currentItem.splitStack(amount);

                if (this.currentItem.stackSize == 0)
                    this.currentItem = null;

                update();
                return itemstack;
            }
        } else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.currentItem != null) {
            ItemStack itemstack = this.currentItem;
            this.currentItem = null;
            update();
            return itemstack;
        } else
            return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.currentItem = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
        update();
    }

    @Override
    public String getInventoryName() {
        return "Rift Injection Pool";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 16;
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
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return InjectorRegistry.hasRecipeForInput(item);
    }
}