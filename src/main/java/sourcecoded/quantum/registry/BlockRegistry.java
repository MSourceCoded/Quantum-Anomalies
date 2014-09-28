package sourcecoded.quantum.registry;

import net.minecraft.block.Block;
import sourcecoded.core.block.AbstractBlockRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;

public class BlockRegistry extends AbstractBlockRegistry {
    public static BlockRegistry instance;
    public static BlockRegistry instance() {
        if (instance == null) instance = new BlockRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        for (QABlocks block : QABlocks.values())
            this.addBlock(block.getBlockName(), block.getBlock());
    }

    @Override
    public AbstractBlockRegistry addBlock(String name, Block blockOBJ) {
        super.addBlock(name, blockOBJ);

        if (blockOBJ instanceof IInjectorRecipe)
            InjectorRegistry.addRecipe((IInjectorRecipe) blockOBJ);

        if (blockOBJ instanceof IVacuumRecipe)
            VacuumRegistry.addRecipe((IVacuumRecipe) blockOBJ);

        return this;
    }
}
