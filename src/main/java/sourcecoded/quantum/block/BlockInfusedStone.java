package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.tile.TileInfusedStone;

public class BlockInfusedStone extends BlockQuantum implements ITileEntityProvider, IBlockRenderHook, IInjectorRecipe {

    public BlockInfusedStone() {
        super();
        this.setBlockName("blockInfusedStone");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);
    }

    public int getRenderType() {
        return SimpleTileProxy.renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileInfusedStone();
    }

    @Override
    public void callbackInventory(TileEntity tile) {
    }

    public float getExplosionResistance(Entity entity) {
        return 15F;
    }

    @Override
    public int getEnergyRequired() {
        return Constants.BLOCK_STANDARD_INFUSION;
    }

    @Override
    public byte getTier() {
        return 0;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.stone, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }
}
