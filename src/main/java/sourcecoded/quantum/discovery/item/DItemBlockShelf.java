package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockShelf extends DiscoveryItem {
    public DItemBlockShelf() {
        super("QA|Shelf");

        this.displayStack = new ItemStack(QABlocks.SHELF.getBlock());

        this.x = 60; this.y = 180;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.FURNACE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(displayStack)));
    }
}