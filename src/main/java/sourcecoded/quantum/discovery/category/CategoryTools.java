package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QAItems;

public class CategoryTools extends DiscoveryCategory {

    public CategoryTools() {
        super("QA|Tools");

        this.displayStack = new ItemStack(QAItems.RIFT_PICKAXE.getItem());

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);
    }
}
