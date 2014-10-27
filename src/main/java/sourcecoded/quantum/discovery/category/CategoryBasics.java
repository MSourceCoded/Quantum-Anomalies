package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QAItems;

public class CategoryBasics extends DiscoveryCategory {

    public CategoryBasics() {
        super("QA|Basics");

        //this.displayStack = new ItemStack(QAItems.JOURNAL.getItem());

        this.setIcon(new ResourceLocation(Constants.MODID, "textures/gui/bookVec.png"));

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(true);
    }
}
