package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.SimpleTileProxy;
import sourcecoded.quantum.tile.TileInjectedStone;

import java.util.List;

public class BlockInjectedStone extends BlockDyeable implements ITileEntityProvider, IInjectorRecipe {

    public BlockInjectedStone() {
        super();
        this.setBlockName("blockInjectedStone");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);

        this.setHasSubtypes(true);
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

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileInjectedStone();
    }

    public float getExplosionResistance(Entity entity) {
        return 15F;
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
        //return new ItemStack(Blocks.stone, 1, 0);
        return new ItemStack(this, 1, 0);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 1);
    }

    @Override
    public CraftingContext getContext() {
        return CraftingContext.getStandardContext();
    }

    public void getSubBlocks(Item unknown, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(this, 1, 0));
        subItems.add(new ItemStack(this, 1, 1));
    }
}
