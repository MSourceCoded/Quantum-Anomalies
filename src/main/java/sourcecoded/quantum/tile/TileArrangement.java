package sourcecoded.quantum.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.block.BlockArrangement;
import sourcecoded.quantum.registry.QABlocks;

public class TileArrangement extends TileDyeable {

    public Object activeRecipe;
    public int tickTime = 0;
    public float renderProgress = 0;

    public void updateEntity() {
        tickTime++;

        if (tickTime % 10 == 0)
            activeRecipe = ((BlockArrangement)QABlocks.ARRANGEMENT.getBlock()).tryCraft(worldObj, xCoord, yCoord, zCoord, false, null);

        if (tickTime == 20)
            tickTime = 0;
    }

    public ItemStack getOutput() {
        ItemStack item = null;
        if (activeRecipe instanceof IArrangementRecipe) {
            item = ((IArrangementRecipe) activeRecipe).getOutput();
        } else if (activeRecipe instanceof IRecipe) {
            item = ((IRecipe) activeRecipe).getRecipeOutput();
        }
        return item;
    }
}
