package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.Instability;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.item.ItemBlockQuantum;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.tile.TileSync;

import java.util.Arrays;
import java.util.List;

public class BlockSync extends BlockDyeable implements ITileEntityProvider, IVacuumRecipe {

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

    @Override
    public List<ItemStack> getIngredients() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Blocks.hopper),
                new ItemStack(Items.clock),
                new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1),
                new ItemStack(QABlocks.INJECTED_STONE.getBlock()),
                new ItemStack(QABlocks.MANIPULATION_STANDARD.getBlock()),
                new ItemStack(Items.nether_star)
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getCatalysts() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(Items.redstone),
                new ItemStack(Items.clock),
                new ItemStack(Items.ender_eye),
        };

        return Arrays.asList(stacks);
    }

    @Override
    public List<ItemStack> getOutputs() {
        ItemStack[] stacks = new ItemStack[] {
                new ItemStack(this, 2),
        };

        return Arrays.asList(stacks);
    }

    @Override
    public int getEnergyRequired() {
        return 100000;
    }

    @Override
    public Instability getInstabilityLevel() {
        return Instability.DIMENSIONAL_SHIFT;
    }
}
