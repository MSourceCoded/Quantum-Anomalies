package sourcecoded.quantum.registry;

import net.minecraft.block.Block;
import sourcecoded.core.block.AbstractBlockRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.block.*;

public class BlockRegistry extends AbstractBlockRegistry {
    public static BlockRegistry instance;
    public static BlockRegistry instance() {
        if (instance == null) instance = new BlockRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        this.addBlock("riftNode", new BlockRiftNode());
        this.addBlock("infusedStone", new BlockInfusedStone());
        this.addBlock("infusedGlass", new BlockInfusedGlass());
        this.addBlock("cornerStone", new BlockCornerstone());
        this.addBlock("gravityNode", new BlockGravityNode());

        this.addBlock("blockCaosHell", new BlockCaosHell());
        this.addBlock("blockCaosEnder", new BlockCaosEnder());

        this.addBlock("blockDebug", new BlockDebug());
        this.addBlock("blockRiftSmelter", new BlockRiftSmelter());
        this.addBlock("blockRiftInjector", new BlockRiftInjector());
    }

    @Override
    public void addBlock(String name, Block blockOBJ) {
        super.addBlock(name, blockOBJ);

        if (blockOBJ instanceof IInjectorRecipe)
            InjectorRegistry.addRecipe((IInjectorRecipe) blockOBJ);
    }
}
