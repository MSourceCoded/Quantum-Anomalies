package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.core.crafting.ICraftableBlock;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.item.ItemBlockQuantum;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.tile.TileSync;
import sourcecoded.quantum.vacuum.recipes.SyncCharged;
import sourcecoded.quantum.vacuum.recipes.SyncStandard;

import java.util.Arrays;
import java.util.List;

public class BlockSync extends BlockDyeable implements ITileEntityProvider, ICraftableBlock {

    public BlockSync() {
        super();
        this.setBlockName("blockSynchronize");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @SuppressWarnings("unchecked")
    public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(this, 1, 0));
        subItems.add(new ItemStack(this, 1, 1));
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
    public Class<? extends ItemBlock> getItemBlock(Block block) {
        return TheItemBlock.class;
    }

    @Override
    public IRecipe[] getRecipes(Block block) {
        VacuumRegistry.addRecipe(new SyncStandard());
        VacuumRegistry.addRecipe(new SyncCharged());
        return new IRecipe[0];
    }

    public static class TheItemBlock extends ItemBlockQuantum {

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(true);
        }

//        public void addInformation(ItemStack item, EntityPlayer player, List list, boolean idk) {
//            list.add(LocalizationUtils.translateLocalWithColours("qa.block.blockSynchronize.lore.0", "{c:ITALIC}One of us.... One of us...."));
//        }

    }
}
