package sourcecoded.quantum.api.event.crafting;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;

/**
 * A set of events relating to Arrangement Table Crafting
 *
 * @author SourceCoded
 */
public class ArrangementCraftingEvent extends Event {

    public IArrangementRecipe recipe;
    public EntityPlayer playerCrafting;
    public World worldObj;

    public TileEntity craftingTile;

    private ArrangementCraftingEvent(IArrangementRecipe recipe, EntityPlayer player, World world, TileEntity tile) {
        this.recipe = recipe;
        this.playerCrafting = player;
        this.worldObj = world;
        this.craftingTile = tile;
    }

    /**
     * Called when the Arrangement table is formed (Obsidian on
     * a crafting table's vertices and shift-clicking a diamond)
     */
    public static class Formed extends Event {

        public World world;
        public int x, y, z;
        public EntityPlayer player;

        public Formed(World world, int x, int y, int z, EntityPlayer player) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.player = player;
        }

    }

    /**
     * Called when a crafting recipe is completed. Use this to unlock
     * discoveries, or whatever else
     *
     * Note that player will be null if the crafting was initiated
     * by a redstone signal
     */
    public static class Complete extends ArrangementCraftingEvent {
        public Complete(IArrangementRecipe recipe, EntityPlayer player, World world, TileEntity tile) {
            super(recipe, player, world, tile);
        }
    }

    /**
     * Called when a player initiates a craft. Cancel this event if you
     * wish to stop the crafting from taking place.
     *
     * Note that player will be null if the crafting was initiated
     * by a redstone signal
     */
    @Cancelable
    public static class Initiated extends ArrangementCraftingEvent {
        public Initiated(IArrangementRecipe recipe, EntityPlayer player, World world, TileEntity tile) {
            super(recipe, player, world, tile);
        }
    }
}
