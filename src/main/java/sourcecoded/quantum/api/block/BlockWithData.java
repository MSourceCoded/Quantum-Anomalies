package sourcecoded.quantum.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

public class BlockWithData {

    public Block block;
    public int metadata;

    public BlockWithData(Block block, int metadata) {
        this.block = block;
        this.metadata = metadata;
    }

}
