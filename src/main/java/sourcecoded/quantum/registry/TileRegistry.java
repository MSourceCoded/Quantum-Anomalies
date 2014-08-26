package sourcecoded.quantum.registry;

import sourcecoded.core.tile.AbstractTileRegistry;
import sourcecoded.quantum.tile.*;

public class TileRegistry extends AbstractTileRegistry {
    public static TileRegistry instance;
    public static TileRegistry instance() {
        if (instance == null) instance = new TileRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        addTile("riftNode", TileRiftNode.class);
        addTile("infusedStone", TileInfusedStone.class);
        addTile("infusedGlass", TileInfusedGlass.class);
        addTile("cornerStone", TileCornerstone.class);
        addTile("gravityNode", TileGravityNode.class);

        addTile("debug", TileDebug.class);
        addTile("riftSmelter", TileRiftSmelter.class);
        addTile("riftInfuser", TileRiftInfuser.class);
    }
}
