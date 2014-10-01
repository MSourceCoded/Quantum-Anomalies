package sourcecoded.quantum.registry;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import sourcecoded.core.block.AbstractBlockRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;

import java.util.HashMap;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    public static void wailaRegister(IWailaRegistrar registrar) {
        BlockRegistry registry = instance();

        HashMap<String, Block> hash = (HashMap<String, Block>) registry.blockMap.clone();

        for (Map.Entry<String, Block> entry : hash.entrySet()) {
            ItemBlock ib = (ItemBlock) ItemBlock.getItemFromBlock(entry.getValue());
            if (ib instanceof IWailaDataProvider)
                registrar.registerBodyProvider((IWailaDataProvider)ib, entry.getValue().getClass());
        }
    }
}
