package sourcecoded.quantum.registry;

import sourcecoded.core.tile.AbstractTileRegistry;
import sourcecoded.quantum.api.entanglement.EntanglementRegistry;

public class TileRegistry extends AbstractTileRegistry {
    public static TileRegistry instance;
    public static TileRegistry instance() {
        if (instance == null) instance = new TileRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        for (QABlocks block : QABlocks.values())
            if (block.tileEntity != null) {
                addTile(block.getBlockName(), block.tileEntity);
                if (block.canEntangle)
                    EntanglementRegistry.whitelistTileBlock(block.getBlock());
            }
    }
}
