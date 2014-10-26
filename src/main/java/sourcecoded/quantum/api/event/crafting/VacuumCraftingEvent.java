package sourcecoded.quantum.api.event.crafting;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;

import java.util.List;

/**
 * A set of events relating to Vacuum Catalyst Crafting
 *
 * @author SourceCoded
 */
public class VacuumCraftingEvent extends Event {

    public IVacuumRecipe recipe;
    public World worldObj;

    public List<IInventory> inputInventories;
    public List<IInventory> outputInventories;
    public List<IInventory> catalystInventories;

    public TileEntity craftingTile;

    private VacuumCraftingEvent(IVacuumRecipe recipe, World world, TileEntity tile) {
        this.recipe = recipe;
        this.worldObj = world;
        this.craftingTile = tile;
    }

    /**
     * Called when a crafting recipe is completed. Use this to unlock
     * discoveries, or whatever else
     */
    public static class Complete extends VacuumCraftingEvent {
        public Complete(IVacuumRecipe recipe, World world, List<IInventory> inputs, List<IInventory> outputs, List<IInventory> catalysts, TileEntity tile) {
            super(recipe, world, tile);
            this.inputInventories = inputs;
            this.outputInventories = outputs;
            this.catalystInventories = catalysts;
        }
    }

    /**
     * Called each time the chamber does a crafting cycle.
     * Cancel this if you wish for the crafting to stop
     *
     * Set the result to ALLOW if you want the chamber to
     * do its instability
     */
    @HasResult
    @Cancelable
    public static class CraftingCycle extends VacuumCraftingEvent {
        public CraftingCycle(IVacuumRecipe recipe, World world, List<IInventory> inputs, List<IInventory> outputs, List<IInventory> catalysts, TileEntity tile) {
            super(recipe, world, tile);
            this.inputInventories = inputs;
            this.outputInventories = outputs;
            this.catalystInventories = catalysts;

            this.setResult(Result.DEFAULT);
        }
    }
}
