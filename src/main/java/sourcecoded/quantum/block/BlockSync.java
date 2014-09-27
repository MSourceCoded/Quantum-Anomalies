package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.item.ItemBlockQuantum;
import sourcecoded.quantum.tile.TileSync;

public class BlockSync extends BlockDyeable implements IInjectorRecipe, ITileEntityProvider {

    public BlockSync() {
        super();
        this.setBlockName("blockSynchronize");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileSync tile = (TileSync) world.getTileEntity(x, y, z);
        tile.onBlockChanged();
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSync();
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileSync tile = (TileSync) world.getTileEntity(x, y, z);
        if (tile != null)
            tile.onDestroy();
        super.breakBlock(world, x, y, z, block, meta);
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

    @Override
    public int getEnergyRequired() {
        return InjectionConstants.INJECTION_STANDARD_MACHINE;
    }

    @Override
    public byte getTier() {
        return 2;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.hopper, 1, 0);      //TEMP
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock(Block block) {
        return TheItemBlock.class;
    }

    public static class TheItemBlock extends ItemBlockQuantum {

        public TheItemBlock(Block block) {
            super(block);
        }

//        public void addInformation(ItemStack item, EntityPlayer player, List list, boolean idk) {
//            list.add(LocalizationUtils.translateLocalWithColours("qa.block.blockSynchronize.lore.0", "{c:ITALIC}One of us.... One of us...."));
//        }
    }
}
