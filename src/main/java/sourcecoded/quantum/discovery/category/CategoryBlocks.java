package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QABlocks;

public class CategoryBlocks extends DiscoveryCategory {

    public CategoryBlocks() {
        super("QA|Blocks");

        this.displayStack = new ItemStack(QABlocks.ARRANGEMENT.getBlock());

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);
    }
}
