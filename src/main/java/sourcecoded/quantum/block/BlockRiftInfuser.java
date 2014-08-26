package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.tile.TileRiftInfuser;
import sourcecoded.quantum.tile.TileRiftSmelter;

public class BlockRiftInfuser extends BlockQuantum implements ITileEntityProvider, IBlockRenderHook {

    public BlockRiftInfuser() {
        super();
        this.setBlockName("blockRiftInfuser");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        this.setBlockBounds(0F, 0F, 0F, 1F, 12/16F, 1F);
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
        return new TileRiftInfuser();
    }

    @Override
    public void callbackInventory(TileEntity tile) {
    }
}
