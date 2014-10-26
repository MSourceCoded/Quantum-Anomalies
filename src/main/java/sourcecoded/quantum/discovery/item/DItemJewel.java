package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

public class DItemJewel extends DiscoveryItem {

    public DItemJewel() {
        super("QA|Jewel");

        this.displayStack = new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem());

        this.x = 100; this.y = 5;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(displayStack)));
    }
}
