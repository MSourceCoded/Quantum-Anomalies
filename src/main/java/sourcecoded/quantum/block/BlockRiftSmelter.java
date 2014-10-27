package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TileRiftSmelter;

public class BlockRiftSmelter extends BlockDyeable implements ITileEntityProvider, IInjectorRecipe {

    public BlockRiftSmelter() {
        super();
        this.setBlockName("blockRiftSmelter");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public int getRenderType() {
        return AdvancedTileProxy.renderID;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileRiftSmelter();
    }

    @Override
    public int getEnergyRequired() {
        return InjectionConstants.INJECTION_STANDARD_MACHINE;
    }

    @Override
    public byte getTier() {
        return 1;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.furnace, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }
}
