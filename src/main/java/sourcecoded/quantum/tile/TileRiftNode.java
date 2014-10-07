package sourcecoded.quantum.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.Point3D;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.api.tileentity.IBindable;
import sourcecoded.quantum.api.tileentity.IDyeable;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.network.MessageVanillaParticle;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.structure.MultiblockLayer;
import sourcecoded.quantum.util.WorldUtils;
import sourcecoded.quantum.vacuum.instability.InstabilityHandler;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.quantum.api.block.Colourizer.LIGHT_BLUE;

public class TileRiftNode extends TileDyeable implements ITileRiftHandler, IBindable {

    public RiftEnergyStorage riftStorage;

    transient int energyUpdateTicker;
    transient int worldInteractionTicker;
    transient int renderTicker;
    transient int radius = 10;

    public transient int maxShockCooldown = 100;
    public int shockCooldown = 0;

    float force = 0.1F;

    boolean crafting = false;
    IVacuumRecipe currentActiveRecipe;

    ArrayList<ItemStack> remainingInputs;

    ArrayList<Point3D> boundPoints = new ArrayList<Point3D>();

    InstabilityHandler currentHandler;
    public boolean unstable;

    public EnergyBehaviour behaviour = EnergyBehaviour.EQUALIZE;

    public MultiblockLayer[] vacuumLayers = new MultiblockLayer[]{
            new MultiblockLayer("ciiiiic", "iiiiiii", "iiiiiii", "iiiiiii", "iiiiiii", "iiiiiii", "ciiiiic", 'c', QABlocks.INJECTED_CORNERSTONE.getBlock(), 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("iiiiiii", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iiiiiii", 'a', Blocks.air, 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("iiiiiii", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iiiiiii", 'a', Blocks.air, 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("iiiiiii", "iaaaaai", "iaaaaai", "iaa aai", "iaaaaai", "iaaaaai", "iiiiiii", 'a', Blocks.air, 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("iiiiiii", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iiiiiii", 'a', Blocks.air, 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("iiiiiii", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iaaaaai", "iiiiiii", 'a', Blocks.air, 'i', QABlocks.INJECTED_STONE.getBlock()),
            new MultiblockLayer("ciiiiic", "iiiiiii", "iiiiiii", "iiiiiii", "iiiiiii", "iiiiiii", "ciiiiic", 'c', QABlocks.INJECTED_CORNERSTONE.getBlock(), 'i', QABlocks.INJECTED_STONE.getBlock()),
    };

    public TileRiftNode() {
        riftStorage = new RiftEnergyStorage(1000000);
    }

    public void cycleBehaviour(EntityPlayer player) {
        if (worldObj.isRemote) return;
        int start = behaviour.ordinal();
        start++;
        if (start >= EnergyBehaviour.values().length)
            start = 0;
        behaviour = EnergyBehaviour.values()[start];

        player.addChatComponentMessage(new ChatComponentText(String.format(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.diagnostic.energyBehaviour", "Behaviour: %s"), behaviour.toString())));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        riftStorage.writeRiftToNBT(nbt);
        nbt.setBoolean("crafting", crafting);


        if (crafting) {
            NBTTagList remaining = new NBTTagList();

            for (ItemStack remainingInput : this.remainingInputs) {
                if (remainingInput != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    remainingInput.writeToNBT(nbttagcompound1);
                    remaining.appendTag(nbttagcompound1);
                }
            }

            nbt.setTag("RemainingItems", remaining);

            NBTTagList catalysts = new NBTTagList();

            for (ItemStack currentCatalyst : getItemsFromInventory(getVacuumCatalysts().get(0), true)) {
                if (currentCatalyst != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    currentCatalyst.writeToNBT(nbttagcompound1);
                    catalysts.appendTag(nbttagcompound1);
                }
            }

            nbt.setTag("Catalysts", catalysts);
        }
        nbt.setBoolean("Unstable", unstable);

        nbt.setInteger("BehaviourIndex", behaviour.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        riftStorage.readRiftFromNBT(nbt);
        crafting = nbt.getBoolean("crafting");
        if (crafting) {
            NBTTagList remaining = nbt.getTagList("RemainingItems", 10);
            this.remainingInputs = new ArrayList<ItemStack>();

            for (int i = 0; i < remaining.tagCount(); ++i) {
                NBTTagCompound nbttagcompound1 = remaining.getCompoundTagAt(i);

                this.remainingInputs.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }

            NBTTagList catalysts = nbt.getTagList("Catalysts", 10);

            ArrayList<ItemStack> catalystList = new ArrayList<ItemStack>();
            for (int i = 0; i < catalysts.tagCount(); ++i) {
                NBTTagCompound nbttagcompound1 = catalysts.getCompoundTagAt(i);

                catalystList.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
            this.currentActiveRecipe = VacuumRegistry.getRecipeForCatalyst(catalystList);
        }
        unstable = nbt.getBoolean("Unstable");

        if (nbt.hasKey("BehaviourIndex"))
            behaviour = EnergyBehaviour.values()[nbt.getInteger("BehaviourIndex")];
    }

    @SuppressWarnings("unchecked")
    public void updateEntity() {
        super.updateEntity();
        energyUpdateTicker++;
        worldInteractionTicker++;

        if (shockCooldown > 0)
            shockCooldown--;

        if (this.worldObj.isRemote) {
            float size = Math.max(1F * ((float) getRiftEnergy() / (float) getMaxRiftEnergy()), 0.1F);
            EntityPlayer renderEntity = FMLClientHandler.instance().getClientPlayerEntity();

            double distance = renderEntity.getDistance(xCoord, yCoord, zCoord);

            Colourizer renderColour = getColour();

            if (crafting) renderColour = Colourizer.WHITE;
            if (unstable) renderColour = Colourizer.RED;

            if (distance < ConfigHandler.getDouble(ConfigHandler.Properties.PARTICLE_RANGE_HIGH)) {
                if (this.worldObj.rand.nextInt(9) == 0)
                    FXManager.riftNodeFX1Larger(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, renderColour);

                if (this.worldObj.rand.nextInt(5) == 0) {
                    FXManager.riftNodeFX1Smaller(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                    if (distance < ConfigHandler.getDouble(ConfigHandler.Properties.PARTICLE_RANGE_LOW) && size > 0.4F && !crafting)
                        FXManager.orbitingFX1(size / 10F, worldObj, xCoord, yCoord, zCoord, renderColour);
                }

                if (crafting) {
                    renderTicker++;

                    if (renderTicker > 10) {
                        FXManager.vacuumFX1(worldObj, xCoord, yCoord, zCoord);
                        FXManager.vacuumFX1(worldObj, xCoord, yCoord, zCoord);
                        FXManager.vacuumFX1(worldObj, xCoord, yCoord, zCoord);
                    }
                } else renderTicker = 0;
            }
        } else {
            if (worldInteractionTicker % 5 == 0)
                checkLightning();

            if (worldInteractionTicker % 5 == 0)
                checkDaylight();

            if (worldInteractionTicker % 40 == 0)
                checkFire();

            //Instability
            if (getRiftEnergy() < ((float) getMaxRiftEnergy() * 0.05F) && worldInteractionTicker % 20 == 0 && RandomUtils.nextInt(0, 10) == 0) {
                int x = RandomUtils.nextInt(xCoord - 15, xCoord + 15);
                int y = RandomUtils.nextInt(yCoord - 2, yCoord + 2);
                int z = RandomUtils.nextInt(zCoord - 15, zCoord + 15);

                switch (getColour()) {
                    case LIGHT_BLUE:
                        if (!worldObj.isDaytime())
                            worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y, z));
                        break;

                    case ORANGE:
                        int ex = RandomUtils.nextInt(xCoord - 7, xCoord + 7);
                        int ez = RandomUtils.nextInt(zCoord - 7, zCoord + 7);
                        worldObj.createExplosion(null, ex, y, ez, RandomUtils.nextFloat(3, 6), true);
                        break;

                    case RED:
                        for (int i = 0; i < RandomUtils.nextInt(10, 20); i++) {
                            int nx = RandomUtils.nextInt(xCoord - 7, xCoord + 7);
                            int nz = RandomUtils.nextInt(zCoord - 7, zCoord + 7);

                            for (int j = yCoord; j > yCoord - 10; j--) {
                                if (worldObj.getBlock(nx, j, nz) == Blocks.air) {
                                    worldObj.setBlock(nx, j, nz, Blocks.fire);
                                    if (worldObj.getBlock(nx, j - 1, nz) != Blocks.air) {
                                        IMessage message = new MessageVanillaParticle("lava", nx, yCoord + RandomUtils.nextFloat(1F, -1F), nz, 1D, 0.2D, 1D, 4);
                                        NetworkHandler.wrapper.sendToDimension(message, worldObj.provider.dimensionId);
                                    }
                                }
                            }
                        }
                        break;
                }
            }

            energyIf:
            if (energyUpdateTicker >= 30) {
                energyUpdateTicker = 0;

                force = 0.1F;
                List<TileEntity> tiles = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, radius, radius, radius, ITileRiftHandler.class);

                for (TileEntity tile : tiles) {
                    if (tile instanceof IDyeable) {
                        Colourizer c = ((IDyeable) tile).getColour();
                        if (c != Colourizer.PURPLE && colour != Colourizer.PURPLE && c != colour) continue;
                    }

                    ITileRiftHandler handler = (ITileRiftHandler) tile;
                    Point3D handlerPoint = new Point3D(tile.xCoord, tile.yCoord, tile.zCoord);
                    if (handler instanceof TileRiftNode && getBehaviour() == EnergyBehaviour.DRAIN && !boundPoints.contains(handlerPoint)) break energyIf;
                    int remaining = handler.getMaxRiftEnergy() - handler.getRiftEnergy();
                    int toSend = Math.min(Math.min(remaining, 1000), getRiftEnergy());          //Max Packet Size

                    boolean canSend = getRiftEnergy() >= 500 && (handler.getRiftEnergy() + toSend) <= handler.getMaxRiftEnergy();      //Packet Minimum

                    if (toSend > 0 && riftStorage.getRiftEnergy() >= toSend && canSend) {

                        if (handler.getBehaviour() == EnergyBehaviour.NOT_ACCEPTING) continue;
                        if (handler.getBehaviour() == EnergyBehaviour.EQUALIZE)
                            if ((handler.getRiftEnergy() + toSend) > getRiftEnergy()) continue;

                        EntityEnergyPacket entity = new EntityEnergyPacket(worldObj, toSend, xCoord, yCoord, zCoord, colour);
                        entity.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);

                        worldObj.spawnEntityInWorld(entity);

                        riftStorage.takeRiftEnergy(toSend);

                        Vec3 direction = Vec3.createVectorHelper(tile.xCoord - xCoord, tile.yCoord - yCoord, tile.zCoord - zCoord);
                        Vec3 normal = direction.normalize();

                        if (entity.motionX < force)
                            entity.motionX += force * normal.xCoord;
                        if (entity.motionY < force)
                            entity.motionY += force * normal.yCoord;
                        if (entity.motionZ < force)
                            entity.motionZ += force * normal.zCoord;

                        update();
                    }
                }
            }

            if (worldInteractionTicker % 10 == 0)
                checkPowered();

            if (crafting && worldInteractionTicker % 20 == 0)
                craftingCycle();

            if (currentHandler != null) {
                if (currentHandler.isAlive())
                    currentHandler.tick();
                else {
                    resetInstability();
                    currentHandler = null;
                }
            } else resetInstability();
        }
    }

    public void checkPowered() {
        if (getColour() != Colourizer.BLACK) return;
        boolean flag = false;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord + 3, yCoord, zCoord)) flag = true;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord - 3, yCoord, zCoord)) flag = true;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord + 3)) flag = true;
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord - 3)) flag = true;

        if (!flag) return;

        boolean craftingReturned = attemptCrafting();
        if (!craftingReturned) {
            setCrafting(false);
            if (currentActiveRecipe != null) {
                //Do the instability

                instability();

                currentActiveRecipe = null;
            }
        }
    }

    public void checkDaylight() {
        if (getColour() == Colourizer.YELLOW) {
            if (worldObj.isDaytime())
                giveRiftEnergy(1);
        } else if (getColour() == Colourizer.BLACK) {
            if (!worldObj.isDaytime())
                giveRiftEnergy(1);
        }
    }

    public void setCrafting(boolean state) {
        this.crafting = state;
        update();
    }

    public void instability() {
        currentHandler = new InstabilityHandler(currentActiveRecipe, currentActiveRecipe.getInstabilityLevel(), this);
        unstable = true;
        update();
    }

    public void resetInstability() {
        unstable = false;
        update();
    }

    @SuppressWarnings("unchecked")
    public boolean attemptCrafting() {
        if (!validMultiblock()) return false;
        if (crafting) return true;

        List<IInventory> imports = getVacuumImports();
        List<IInventory> exports = getVacuumExports();
        List<IInventory> catalystImports = getVacuumCatalysts();

        if (imports.size() == 0 || exports.size() == 0 || catalystImports.size() == 0) return false;

        IVacuumRecipe matchingRecipe = VacuumRegistry.getRecipeForCatalyst(trimNull(getItemsFromInventory(catalystImports.get(0), true)));
        if (matchingRecipe == null) return false;
        currentActiveRecipe = matchingRecipe;
        //if (matchingRecipe.getEnergyRequired() > getRiftEnergy()) return false;

        remainingInputs = new ArrayList<ItemStack>(matchingRecipe.getIngredients());

        setCrafting(true);

        vacuumEnergy(currentActiveRecipe.getVacuumEnergyStart());

        return true;
    }

    public void craftingCycle() {
        //if (!validMultiblock() || getRiftEnergy() < currentActiveRecipe.getEnergyRequired()) {
        if (!validMultiblock()) {
            setCrafting(false);
            //Do Instability here
            instability();
            return;
        }

        List<IInventory> imports = getVacuumImports();
        List<IInventory> exports = getVacuumExports();

        if (remainingInputs.size() > 0) {
            ItemStack nextItem = remainingInputs.get(0);

            boolean itemFound = false;

            inventoryFindLoop:
            for (IInventory inventory : imports) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack current = inventory.getStackInSlot(i);
                    if (current == null) continue;
                    if (current.isItemEqual(nextItem)) {
                        current.stackSize -= 1;
                        nextItem.stackSize -= 1;
                        if (current.stackSize > 0)
                            inventory.setInventorySlotContents(i, current);
                        else
                            inventory.setInventorySlotContents(i, null);

                        vacuumEnergy(currentActiveRecipe.getVacuumEnergyPerItem());

                        if (nextItem.stackSize <= 0)
                            remainingInputs.remove(0);

                        itemFound = true;
                        break inventoryFindLoop;
                    }
                }
            }

            if (!itemFound) {
                setCrafting(false);
                //Do Instability here

                QuantumAnomalies.logger.warn("Item Missing: " + nextItem.getDisplayName() + ". Stopping crafting.");

                instability();
            }
        } else {
            IInventory inventory = null;

            for (IInventory cInventory : exports) {
                if (getItemsFromInventory(cInventory, true).contains(null)) {
                    inventory = cInventory;
                    break;
                }
            }

            if (inventory != null) {
                insertItems(currentActiveRecipe.getOutputs(), inventory);
            }

            currentActiveRecipe = null;
            setCrafting(false);
        }
    }

    public void vacuumEnergy(int energy) {
//        int result = takeRiftEnergy(energy);
//        if (result != energy) {
//            setCrafting(false);
//            instability();
//        }
    }

    public void insertItems(List<ItemStack> stacks, IInventory inventory) {
        for (ItemStack stack : stacks) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stackInSlot = inventory.getStackInSlot(i);
                if (stackInSlot == null) {
                    inventory.setInventorySlotContents(i, stack);
                    break;
                }
                if (stackInSlot.isItemEqual(stack)) {
                    stackInSlot.stackSize += stack.stackSize;
                    inventory.setInventorySlotContents(i, stackInSlot);
                    break;
                }
            }
        }

        for (int i = 0; i < 10; i++)
            NetworkHandler.wrapper.sendToAllAround(new MessageVanillaParticle("happyVillager", xCoord + RandomUtils.nextDouble(0F, 1F), yCoord + RandomUtils.nextDouble(0F, 1F), zCoord + RandomUtils.nextDouble(0F, 1F), 0F, 0F, 0F, 1), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 32));
    }

    @SuppressWarnings("unchecked")
    public List<IInventory> getVacuumImports() {
        ArrayList<IInventory> inventories = new ArrayList<IInventory>();
        List<TileCornerstone> cornerstones = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, 3, 3, 3, TileCornerstone.class);
        for (TileCornerstone cornerstone : cornerstones) {
            if (cornerstone.getColour() != Colourizer.LIME) continue;
            inventories.addAll(cornerstone.getAdjacentInventories());
        }
        return inventories;
    }

    @SuppressWarnings("unchecked")
    public List<IInventory> getVacuumExports() {
        ArrayList<IInventory> inventories = new ArrayList<IInventory>();
        List<TileCornerstone> cornerstones = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, 3, 3, 3, TileCornerstone.class);
        for (TileCornerstone cornerstone : cornerstones) {
            if (cornerstone.getColour() != Colourizer.ORANGE) continue;
            inventories.addAll(cornerstone.getAdjacentInventories());
        }
        return inventories;
    }

    @SuppressWarnings("unchecked")
    public List<IInventory> getVacuumCatalysts() {
        ArrayList<IInventory> inventories = new ArrayList<IInventory>();
        List<TileCornerstone> cornerstones = WorldUtils.searchForTile(worldObj, xCoord, yCoord, zCoord, 3, 3, 3, TileCornerstone.class);
        for (TileCornerstone cornerstone : cornerstones) {
            if (cornerstone.getColour() != Colourizer.WHITE) continue;
            inventories.addAll(cornerstone.getAdjacentInventories());
        }
        return inventories;
    }

    public List<ItemStack> getItemsFromInventory(IInventory inventory, boolean includeNull) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack currentItemstack = inventory.getStackInSlot(i);
            if (includeNull) stacks.add(currentItemstack);
            else if (currentItemstack != null) stacks.add(currentItemstack);
        }
        return stacks;
    }

    public List trimNull(List list) {
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i) == null) list.remove(i);
            else if (list.get(i) != null) return list;
        }
        return list;
    }

    public boolean validMultiblock() {
        if (getColour() != Colourizer.BLACK) return false;
        for (int i = 0; i < vacuumLayers.length; i++) {
            MultiblockLayer layer = vacuumLayers[i];
            if (!layer.valid(worldObj, xCoord, yCoord + (i - 3), zCoord))
                return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public void checkLightning() {
        if (getColour() != LIGHT_BLUE) return;
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord - radius, 0, zCoord - radius, xCoord + radius, 256, zCoord + radius);
        List bolts = worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, bb);

        for (Object entity : worldObj.weatherEffects) {
            if (entity instanceof EntityLightningBolt) {          //TODO check locale
                EntityLightningBolt bolt = (EntityLightningBolt) entity;
                if (bb.isVecInside(Vec3.createVectorHelper(bolt.posX, bolt.posY, bolt.posZ)))
                    bolts.add(bolt);
            }
        }

        if (shockCooldown == 0)
            giveRiftEnergy(RandomUtils.nextInt(100000, 300000) * bolts.size());             //Bolt min/max
        if (shockCooldown == 0 && bolts.size() > 0) {
            IMessage message = new MessageVanillaParticle("hugeexplosion", xCoord + 0.5, yCoord + 0.6, zCoord + 0.5, 0D, 0.2D, 0D, 1);
            NetworkHandler.wrapper.sendToDimension(message, worldObj.provider.dimensionId);

            shockCooldown = maxShockCooldown;
            update();
        }
    }

    public void checkFire() {
        if (getColour() != Colourizer.RED) return;

        boolean didThing = false;

        List list = WorldUtils.searchForBlock(worldObj, xCoord, yCoord, zCoord, radius, radius, radius, BlockFire.class);
        for (Object block : list)
            if (block instanceof BlockFire) {
                if (giveRiftEnergy(10) != 0) {          //Fire per tick
                    didThing = true;
                }
            }

        if (didThing) {
            IMessage message = new MessageVanillaParticle("lava", xCoord + 0.5, yCoord + 0.6, zCoord + 0.5, 0D, 0.2D, 0D, Math.max(list.size() / 3, 1));
            NetworkHandler.wrapper.sendToDimension(message, worldObj.provider.dimensionId);
        }

        update();
    }

    @Override
    public int takeRiftEnergy(int amount) {
        update();
        return riftStorage.takeRiftEnergy(amount);
    }

    @Override
    public int giveRiftEnergy(int amount) {
        update();
        return riftStorage.giveRiftEnergy(amount);
    }

    @Override
    public int getRiftEnergy() {
        return riftStorage.getRiftEnergy();
    }

    @Override
    public int getMaxRiftEnergy() {
        return riftStorage.getMaxRiftEnergy();
    }

    @Override
    public EnergyBehaviour getBehaviour() {
        return behaviour;
    }

    @Override
    public boolean tryBind(EntityPlayer player, int x, int y, int z, boolean silent) {
        Point3D point = new Point3D(x, y, z);
        //TODO make DRAIN nodes have to bind other nodes
        if (!(worldObj.getTileEntity(x, y, z) instanceof TileRiftNode)) return false;

        if (boundPoints.contains(point)) {
            boundPoints.remove(point);
            String bind = "qa.sceptre.focus.bind.bindingRemoved";
            player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours(bind, bind)));
        } else {
            boundPoints.add(point);
            String bind = "qa.sceptre.focus.bind.bindingComplete";
            player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours(bind, bind)));
        }

        return false;
    }
}