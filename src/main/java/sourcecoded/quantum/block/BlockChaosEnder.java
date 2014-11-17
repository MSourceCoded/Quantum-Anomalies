package sourcecoded.quantum.block;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BlockChaosEnder extends BlockChaosHell {

    public BlockChaosEnder() {
        this.setBlockName("blockChaosEnder");
        this.setBlockTextureName("caosBrickEnd");
        this.setHardness(5F);
    }

    @Override
    public ItemStack getInput() {
        return new ItemStack(Blocks.end_stone);
    }
}