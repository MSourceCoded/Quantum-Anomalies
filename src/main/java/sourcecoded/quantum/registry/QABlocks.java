package sourcecoded.quantum.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.block.*;
import sourcecoded.quantum.tile.*;

public enum QABlocks {

    INJECTED_STONE("blockInjectedStone", new BlockInjectedStone(), TileInjectedStone.class),
    CHAOS_ENDER("blockChaosEnder", new BlockChaosEnder()),
    CHAOS_HELL("blockChaosHell", new BlockChaosHell()),
    INJECTED_CORNERSTONE("blockInjectedCornerstone", new BlockCornerstone(), TileCornerstone.class),
    INJECTED_GLASS("blockInjectedGlass", new BlockInjectedGlass(), TileInjectedGlass.class),
    RIFT_INJECTION_POOL("blockInjectionPool", new BlockRiftInjector(), TileRiftInjector.class),
    RIFT_NODE("blockRiftNode", new BlockRiftNode(), TileRiftNode.class),
    RIFT_SMELTER("blockRiftFurnace", new BlockRiftSmelter(), TileRiftSmelter.class),
    MANIPULATION_STANDARD("blockManipulation", new BlockManipulation(Material.rock), TileManipulation.class),
    MANIPULATION_WATER("blockManipulationWater", new BlockManipulation(Material.water), TileManipulation.class),
    ARRANGEMENT("blockArrangementTable", new BlockArrangement(), TileArrangement.class),
    SYNC("blockSynchronize", new BlockSync(), TileSync.class);

    public String identifier;
    public Block instance;

    public boolean canEntangle = true;

    public Class<? extends TileEntity> tileEntity = null;

    QABlocks(String identifier, Block instance) {
        this.identifier = identifier;
        this.instance = instance;

        canEntangle = true;
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
