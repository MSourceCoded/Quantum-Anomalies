package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockInjector extends DiscoveryItem {

    public DItemBlockInjector() {
        super("QA|Injection");

        this.displayStack = new ItemStack(QABlocks.RIFT_INJECTION_POOL.getBlock());

        this.x = 100; this.y = 90;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.NODE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(new ItemStack(QABlocks.RIFT_INJECTION_POOL.getBlock()))));
    }

}
