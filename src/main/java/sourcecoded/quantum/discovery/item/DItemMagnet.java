package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemMagnet extends DiscoveryItem {

    public DItemMagnet() {
        super("QA|Magnet");

        this.displayStack = new ItemStack(QAItems.RIFT_MAGNET.getItem());

        this.x = 100; this.y = 120;

        this.setSpecial(false);

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.parents.add(QADiscoveries.Item.STAR.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(displayStack)));
    }
}
