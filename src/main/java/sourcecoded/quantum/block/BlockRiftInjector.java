package sourcecoded.quantum.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableBlock;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.block.IDiagnostic;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.client.renderer.block.IBlockRenderHook;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;
import sourcecoded.quantum.tile.TileRiftInjector;

public class BlockRiftInjector extends BlockDyeable implements ITileEntityProvider, IBlockRenderHook, ICraftableBlock, IDiagnostic {

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
        ((TileRiftInjector) tile).rift.riftAmount = ((TileRiftInjector) tile).rift.riftCapacity;
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

    @Override
    public IRecipe[] getRecipes(Block block) {
        //GameRegistry.addShapedRecipe(new ItemStack(this, 1), "   ", "o o", "dod", 'o', Blocks.obsidian, 'd', QAItems.OBSIDIAN_JEWEL.getItem());
        //GameRegistry.addShapedRecipe(new ItemStack(this, 1), "   ", "o o", "dod", 'o', QABlocks.INJECTED_STONE.getBlock(), 'd', QAItems.OBSIDIAN_JEWEL.getItem());

        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(this, 1), "   ", "o o", "dod", 'o', Blocks.obsidian, 'd', QAItems.OBSIDIAN_JEWEL.getItem()));
        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(this, 1), "   ", "o o", "dod", 'o', QABlocks.INJECTED_STONE.getBlock(), 'd', QAItems.OBSIDIAN_JEWEL.getItem()));
        return new IRecipe[0];
    }

    @Override
    public void onDiagnose(DiagnosticsPhase phase, World world, int x, int y, int z, EntityPlayer player) {
        if (phase == DiagnosticsPhase.AFTER) {
            TileRiftInjector injector = (TileRiftInjector) world.getTileEntity(x, y, z);
            if (injector == null) return;
            String format = LocalizationUtils.translateLocalWithColours(getUnlocalizedName() + ".diagnose", "Tier: %s");
            player.addChatComponentMessage(new ChatComponentText(String.format(format, injector.getTier())));
        }
    }
}
