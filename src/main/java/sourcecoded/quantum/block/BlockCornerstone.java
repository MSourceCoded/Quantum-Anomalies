package sourcecoded.quantum.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TileCornerstone;

public class BlockCornerstone extends BlockInjectedStone {

    public BlockCornerstone() {
        super();
        this.setBlockName("blockCornerstone");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);
    }

    public int getRenderType() {
        return AdvancedTileProxy.renderID;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.UP || side == ForgeDirection.DOWN;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCornerstone();
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
        return new ItemStack(Blocks.stonebrick, 1, 3);
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(this, 1, 0);
    }
}
