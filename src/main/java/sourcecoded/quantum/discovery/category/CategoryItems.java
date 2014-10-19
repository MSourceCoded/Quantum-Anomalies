package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

public class CategoryItems extends DiscoveryCategory {

    public CategoryItems() {
        super("QA|Items");

        this.displayStack = new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem());

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);
    }
}
