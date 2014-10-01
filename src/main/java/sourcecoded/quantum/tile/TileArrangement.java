package sourcecoded.quantum.tile;

import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.block.BlockArrangement;
import sourcecoded.quantum.registry.QABlocks;

public class TileArrangement extends TileDyeable {

    public IArrangementRecipe activeRecipe;
    public int tickTime = 0;
    public float renderProgress = 0;

    public void updateEntity() {
        tickTime++;

        if (tickTime % 10 == 0)
            activeRecipe = ((BlockArrangement)QABlocks.ARRANGEMENT.getBlock()).tryCraft(worldObj, xCoord, yCoord, zCoord, false);

        if (tickTime == 20)
            tickTime = 0;
    }

}
