package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockInjected extends DiscoveryItem {
    public DItemBlockInjected() {
        super("QA|InjectedBlocks");

        this.displayStack = new ItemStack(QABlocks.INJECTED_STONE.getBlock(), 1, 1);

        this.x = 60; this.y = 130;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.INJECTION.get().getKey());
        this.addParent(QADiscoveries.Item.ETCHED_STONE.get().getKey());
        this.addParent(QADiscoveries.Item.ETCHED_CORNER.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(displayStack)));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(new ItemStack(QABlocks.INJECTED_CORNERSTONE.getBlock(), 1, 1))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(new ItemStack(QABlocks.INJECTED_GLASS.getBlock()))));
    }
}