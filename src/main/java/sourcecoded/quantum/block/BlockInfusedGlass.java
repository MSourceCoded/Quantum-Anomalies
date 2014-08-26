package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.tile.TileInfusedGlass;

public class BlockInfusedGlass extends BlockQuantum implements ITileEntityProvider, IBlockRenderHook {

    public BlockInfusedGlass() {
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
        return new TileInfusedGlass();
    }

    @Override
    public void callbackInventory(TileEntity tile) {
    }

    public float getExplosionResistance(Entity entity) {
        return 15F;
    }
}
