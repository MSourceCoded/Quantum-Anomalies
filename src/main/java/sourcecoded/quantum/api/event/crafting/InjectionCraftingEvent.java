package sourcecoded.quantum.api.event.crafting;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.api.injection.IInjectorRecipe;

/**
 * A set of events relating to Injection Pool Crafting
 *
 * @author SourceCoded
 */
public class InjectionCraftingEvent extends Event {

    public IInjectorRecipe recipe;
    public World worldObj;

    public TileEntity craftingTile;

    private InjectionCraftingEvent(IInjectorRecipe recipe, World world, TileEntity tile) {
        this.recipe = recipe;
        this.worldObj = world;
        this.craftingTile = tile;
    }

    /**
     * Called when a crafting recipe is completed. Use this to unlock
     * discoveries, or whatever else
     */
    public static class Complete extends InjectionCraftingEvent {
        public ItemStack resultingItem;

        public Complete(IInjectorRecipe recipe, World world, ItemStack craftedItem, TileEntity tile) {
            super(recipe, world, tile);
            this.resultingItem = craftedItem;
        }
    }

    /**
     * Called when the Injector is checking whether or not
     * the crafting process can take place. Cancel if you wish
     * for this to stop
     */
    @Cancelable
    public static class Validating extends InjectionCraftingEvent {
        public ItemStack inventory;

        public Validating(IInjectorRecipe recipe, World world, ItemStack inventory, TileEntity tile) {
            super(recipe, world, tile);
            this.inventory = inventory;
        }
    }
}
