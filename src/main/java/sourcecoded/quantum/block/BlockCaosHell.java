package sourcecoded.quantum.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.block.IRiftMultiplier;
import sourcecoded.quantum.item.ItemQuantum;
import sourcecoded.quantum.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class BlockCaosHell extends BlockQuantum implements IRiftMultiplier {

    public BlockCaosHell() {
        this.setBlockName("blockCaosHell");
        this.setBlockTextureName("caosBrickHell");
        this.setHardness(5F);
    }

    public int quantityDropped(Random rnd) {
        return rnd.nextInt(5) + 5;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return ItemRegistry.instance().getItemByName("itemCaosShard");
    }

    @Override
    public float getRiftMultiplication(RiftMultiplierTypes type) {
        switch (type) {
            case ENERGY_USAGE:
                return 1.25F;
            case SPEED:
                return 0.95F;
            case PRODUCTION:
                return 1.01F;
            default:
                return 1F;
        }
    }
}
