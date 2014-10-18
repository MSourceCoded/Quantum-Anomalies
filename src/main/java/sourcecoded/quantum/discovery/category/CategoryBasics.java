package sourcecoded.quantum.discovery.category;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

public class CategoryBasics extends DiscoveryCategory {

    public CategoryBasics(int id) {
        super("QA|Basics" + id);
        if (id == 4)
            this.setDisplayItemStack(new ItemStack(QABlocks.INJECTED_STONE.getBlock()));
        else if (id == 3)
            this.setDisplayItemStack(new ItemStack(QAItems.SCEPTRE.getItem()));
        else
            this.setIcon(new ResourceLocation(Constants.MODID, "textures/items/string.png"));

        this.setUnlockedByDefault(id == 3);
        this.setHiddenByDefault(id == 4);
    }

}
