package sourcecoded.quantum.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.tile.TileInjectedGlass;

public class BlockInjectedGlass extends BlockInjectedStone {

    public BlockInjectedGlass() {
        super();
        this.setBlockName("blockInjectedGlass");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);
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
}
