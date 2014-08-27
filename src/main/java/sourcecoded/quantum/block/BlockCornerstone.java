package sourcecoded.quantum.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.client.renderer.block.AdvancedTileProxy;
import sourcecoded.quantum.tile.TileCornerstone;

public class BlockCornerstone extends BlockQuantum implements ITileEntityProvider, IInjectorRecipe {

    public BlockCornerstone() {
        super();
        this.setBlockName("blockCornerStone");
        this.setBlockTextureName("infusedStone");
        this.setHardness(5F);
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

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCornerstone();
    }

    public float getExplosionResistance(Entity entity) {
        return 15F;
    }

    @Override
    public int getEnergyRequired() {
        return Constants.BLOCK_STANDARD_INFUSION;
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
