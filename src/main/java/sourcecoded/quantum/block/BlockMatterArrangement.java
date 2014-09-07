package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.api.injection.InjectionConstants;
import sourcecoded.quantum.tile.TileInjectedGlass;

public class BlockMatterArrangement extends BlockDyeable implements ITileEntityProvider {

    public BlockMatterArrangement() {
        super();
        this.setBlockName("blockMatterArrangement");
        this.setBlockTextureName("infusedStone");
        this.setHardness(6F);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileInjectedGlass();
    }
}
