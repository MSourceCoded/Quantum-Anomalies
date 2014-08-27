package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.tile.TileRiftSmelter;

public class BlockRiftSmelter extends BlockQuantum implements ITileEntityProvider, IBlockRenderHook, IInjectorRecipe {

    public BlockRiftSmelter() {
        super();
        this.setBlockName("blockRiftSmelter");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public int getRenderType() {
        return AdvancedTileProxy.renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileRiftSmelter();
    }

    @Override
    public void callbackInventory(TileEntity tile) {
    }

    @Override
    public int getEnergyRequired() {
        return 1000;
    }

    @Override
    public byte getTier() {
        return 0;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.furnace, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }
}
