package sourcecoded.quantum.registry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.block.*;
import sourcecoded.quantum.tile.*;

public enum QABlocks {

    CHAOS_ENDER("blockChaosEnder", new BlockChaosEnder()),
    CHAOS_HELL("blockChaosHell", new BlockChaosHell()),
    INJECTED_CORNERSTONE("blockInjectedCornerstone", new BlockCornerstone(), TileCornerstone.class),
    INJECTED_GLASS("blockInjectedGlass", new BlockInjectedGlass(), TileInjectedGlass.class),
    INJECTED_STONE("blockInjectedStone", new BlockInjectedStone(), TileInjectedStone.class),
    RIFT_INJECTION_POOL("blockInjectionPool", new BlockRiftInjector(), TileRiftInjector.class),
    RIFT_NODE("blockRiftNode", new BlockRiftNode(), TileRiftNode.class),
    RIFT_SMELTER("blockRiftFurnace", new BlockRiftSmelter(), TileRiftSmelter.class);

    public String identifier;
    public Block instance;

    public Class<? extends TileEntity> tileEntity = null;

    QABlocks(String identifier, Block instance) {
        this.identifier = identifier;
        this.instance = instance;
    }

    QABlocks(String identifier, Block instance, Class<? extends TileEntity> tile) {
        this.identifier = identifier;
        this.instance = instance;

        this.tileEntity = tile;
    }

    public String getBlockName() {
        return identifier;
    }

    public Block getBlock() {
        return instance;
    }

}
