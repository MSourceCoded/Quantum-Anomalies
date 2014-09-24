package sourcecoded.quantum.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.energy.RiftEnergyStorage;
import sourcecoded.quantum.api.tileentity.IDyeable;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;
import sourcecoded.quantum.entity.EntityEnergyPacket;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.network.MessageVanillaParticle;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.structure.MultiblockLayer;
import sourcecoded.quantum.utils.WorldUtils;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.quantum.api.block.Colourizer.LIGHT_BLUE;

public class TileRiftNode extends TileDyeable implements ITileRiftHandler {

    public RiftEnergyStorage riftStorage;

    transient int energyUpdateTicker;
    transient int worldInteractionTicker;
    transient int radius = 10;

    public transient int maxShockCooldown = 100;
    public int shockCooldown = 0;

    float force = 0.1F;

    boolean crafting = false;
    IVacuumRecipe currentActiveRecipe;

    public MultiblockLayer[] vacuumLayers = new MultiblockLayer[] {
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

            if (distance < ConfigHandler.getDouble(ConfigHandler.Properties.PARTICLE_RANGE_HIGH)) {
                if (this.worldObj.rand.nextInt(9) == 0)
                    FXManager.riftNodeFX1Larger(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, getColour());

                if (this.worldObj.rand.nextInt(5) == 0) {
                    FXManager.riftNodeFX1Smaller(size, worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
                    if (distance < ConfigHandler.getDouble(ConfigHandler.Properties.PARTICLE_RANGE_LOW) && size > 0.4F)
                        FXManager.orbitingFX1(size / 10F, worldObj, xCoord, yCoord, zCoord, getColour());
                }
            }
        } else {
            if (worldInteractionTicker % 5 == 0)
                checkLightning();

            if (worldInteractionTicker % 40 == 0)
                checkFire();

            //Instability
            if (getRiftEnergy() < ((float)getMaxRiftEnergy() * 0.05F) && worldInteractionTicker % 20 == 0 && RandomUtils.nextInt(0, 10) == 0) {
                int x = RandomUtils.nextInt(xCoord - 15, xCoord + 15);
                int y = RandomUtils.nextInt(yCoord - 2, yCoord + 2);
                int z = RandomUtils.nextInt(zCoord - 15, zCoord + 15);

                switch(getColour()) {
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

            checkPowered();

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
                    int remaining = handler.getMaxRiftEnergy() - handler.getRiftEnergy();
                    int toSend = Math.min(Math.min(remaining, 3000), getRiftEnergy());          //Max Packet Size

                    boolean canSend = getRiftEnergy() >= 1000 && (handler.getRiftEnergy() + toSend) <= handler.getMaxRiftEnergy();      //Packet Minimum

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
            crafting = false;
            if (currentActiveRecipe != null) {
                //Do the instability
                currentActiveRecipe = null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean attemptCrafting() {
        if (!validMultiblock()) return false;

        List<IInventory> imports = getVacuumImports();
        List<IInventory> exports = getVacuumExports();
        List<IInventory> catalystImports = getVacuumCatalysts();

        if (imports.size() == 0 || exports.size() == 0 || catalystImports.size() == 0) return false;

        IVacuumRecipe matchingRecipe = VacuumRegistry.getRecipeForCatalyst(trimNull(getItemsFromInventory(catalystImports.get(0), true)));
        if (matchingRecipe == null) return false;
        currentActiveRecipe = matchingRecipe;

        crafting = true;

        return true;
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
        for (int i = list.size(); i > 0; i--) {
            if (list.get(i) == null) list.remove(i);
            if (list.get(i) != null) return list;
        }
        return list;
    }

    public boolean validMultiblock() {
        if (getColour() != Colourizer.BLACK) return false;
        for (int i = 0; i<vacuumLayers.length; i++) {
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
            giveRiftEnergy(RandomUtils.nextInt(300000, 700000) * bolts.size());             //Bolt min/max
        if (bolts.size() > 0) {
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
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        riftStorage.writeRiftToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        riftStorage.readRiftFromNBT(nbt);
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
        return EnergyBehaviour.EQUALIZE;
    }
}
