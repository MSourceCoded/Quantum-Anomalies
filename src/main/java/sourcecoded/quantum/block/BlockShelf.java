package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.CraftingContext;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TileShelf;

public class BlockShelf extends BlockDyeable implements ITileEntityProvider, IInjectorRecipe {

    public BlockShelf() {
        this.setBlockTextureName("infusedStone");
        this.setBlockName("blockQuantumBookshelf");
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

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileShelf();
    }

    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return 2.5F;
    }

    @Override
    public int getEnergyRequired() {
        return (int) (InjectionConstants.INJECTION_STANDARD_BLOCK * 1.5F);
    }

    @Override
    public byte getTier() {
        return 2;
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.bookshelf);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1);
    }

    @Override
    public CraftingContext getContext() {
        return new CraftingContext().setOreDictionary(true);
    }
}
