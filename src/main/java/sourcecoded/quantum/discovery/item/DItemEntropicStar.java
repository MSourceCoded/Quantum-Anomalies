package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemEntropicStar extends DiscoveryItem {

    public DItemEntropicStar() {
        super("QA|Star");

        this.displayStack = new ItemStack(QAItems.ENTROPIC_STAR.getItem());

        this.x = 100; this.y = 85;

        this.setSpecial(true);

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.parents.add(QADiscoveries.Item.JEWEL_CHARGED.get().getKey());
        this.parents.add(QADiscoveries.Item.VACUUM.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(displayStack)));
    }
}
