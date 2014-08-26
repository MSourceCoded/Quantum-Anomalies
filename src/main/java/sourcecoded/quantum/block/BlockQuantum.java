package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.inventory.QATabs;

import static sourcecoded.core.util.LocalizationUtils.prefix;

/**
 * Base Class
 */
public class BlockQuantum extends Block {

    public BlockQuantum(Material mat) {
        super(mat);
        this.setCreativeTab(QATabs.quantumTab);
    }

    public BlockQuantum() {
        this(Material.rock);
    }

    public Block setBlockTextureName(String name) {
        return super.setBlockTextureName(prefix(Constants.MODID, name));
    }

    public Block setBlockName(String name) {
        return super.setBlockName(prefix(Constants.MODID_SHORT, name));
    }

}
