package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QABlocks;

public class CategoryBlocks extends DiscoveryCategory {

    public CategoryBlocks() {
        super("QA|Blocks");

        //this.displayStack = new ItemStack(QABlocks.ARRANGEMENT.getBlock());

        this.setIcon(new ResourceLocation(Constants.MODID, "textures/gui/blockVec.png"));

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);
    }
}
