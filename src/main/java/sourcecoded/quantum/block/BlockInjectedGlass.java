package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TileInjectedGlass;

import java.util.List;

public class BlockInjectedGlass extends BlockInjectedStone {

    public BlockInjectedGlass() {
        super();
        this.setBlockName("blockInjectedGlass");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);
        this.setHasSubtypes(false);
    }

    public int getRenderType() {
        return AdvancedTileProxy.renderID;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileInjectedGlass();
    }

    @Override
    public int getEnergyRequired() {
        return InjectionConstants.INJECTION_STANDARD_BLOCK;
    }

    @Override
    public byte getTier() {
        return 0;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.glass, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }

    public void getSubBlocks(Item unknown, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(this, 1, 0));
    }
}
