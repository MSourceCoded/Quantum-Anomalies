package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.tile.TileRiftSmelter;

public class BlockRiftSmelter extends BlockQuantum implements ITileEntityProvider, IBlockRenderHook {

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
}
