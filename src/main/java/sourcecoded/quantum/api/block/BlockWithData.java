package sourcecoded.quantum.api.block;

import net.minecraft.block.Block;

/**
 * A class that is an instance of a block, mostly
 * used for compacting storage of Blocks in an
 * ArrayList
 *
 * @author SourceCoded
 */
public class BlockWithData {

    public Block block;
    public int metadata;

    public BlockWithData(Block block, int metadata) {
        this.block = block;
        this.metadata = metadata;
    }

}
