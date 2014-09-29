package sourcecoded.quantum.block;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.core.block.IBlockHasItem;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.inventory.QATabs;
import sourcecoded.quantum.item.ItemBlockQuantum;
import sourcecoded.quantum.api.tileentity.IDyeable;

import java.util.List;

import static sourcecoded.core.util.LocalizationUtils.prefix;

/**
 * Base Class
 */
public class BlockQuantum extends Block implements IBlockHasItem {

    public String customName;

    public BlockQuantum(Material mat) {
        super(mat);
        this.setCreativeTab(QATabs.quantumTab);
    }

    public BlockQuantum() {
        this(Material.rock);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof IDyeable)
                return ((IDyeable) tile).getColour().ordinal();
        }

        return 0;
    }

    public Block setBlockTextureName(String name) {
        return super.setBlockTextureName(prefix(Constants.MODID, name));
    }

    public Block setBlockName(String name) {
        this.customName = name;
        return super.setBlockName(prefix(Constants.MODID_SHORT, name));
    }

    public String getUnlocalizedName() {
        return "qa.blocks." + customName;
    }

    public String getLocalizedName() {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName() + ".name", getUnlocalizedName() + ".name");
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock(Block block) {
        return ItemBlockQuantum.class;
    }
}
