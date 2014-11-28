package sourcecoded.quantum.block;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.core.crafting.ICraftableBlock;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.crafting.vacuum.VacuumPlayer;
import sourcecoded.quantum.tile.TilePlayer;

import java.util.List;

public class BlockPlayer extends BlockDyeable implements ITileEntityProvider, ICraftableBlock, IWailaDataProvider {

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

    @Override
    public IRecipe[] getRecipes(Block block) {
        VacuumRegistry.addRecipe(new VacuumPlayer());
        return new IRecipe[0];
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        currenttip.add("Bound: " + ((TilePlayer)accessor.getTileEntity()).getInventoryName());
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
