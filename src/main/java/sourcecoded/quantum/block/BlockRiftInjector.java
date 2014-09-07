package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.tile.IDyeable;
import sourcecoded.quantum.tile.TileRiftInjector;

public class BlockRiftInjector extends BlockDyeable implements ITileEntityProvider, IBlockRenderHook {

    public BlockRiftInjector() {
        super();
        this.setBlockName("blockRiftInjector");
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
        return new TileRiftInjector();
    }

    @Override
    public void callbackInventory(TileEntity tile) {
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xo, float yo, float zo) {
        if (super.onBlockActivated(world, x, y, z, player, side, xo, yo, zo)) return true;

        if (!world.isRemote) {
            TileRiftInjector tile = (TileRiftInjector) world.getTileEntity(x, y, z);

            tile.click(player);

            return true;
        }
        return true;
    }
}
