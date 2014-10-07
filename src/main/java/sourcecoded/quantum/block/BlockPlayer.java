package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TilePlayer;

public class BlockPlayer extends BlockDyeable implements ITileEntityProvider {

    public BlockPlayer() {
        super();

        this.setBlockName("blockPlayerEntanglement");
        this.setBlockTextureName("infusedStone");
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

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
        TilePlayer tile = (TilePlayer) world.getTileEntity(x, y, z);
        if (living instanceof EntityPlayer && tile != null)
            tile.setOwner(((EntityPlayer) living).getUniqueID().toString());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TilePlayer();
    }
}
