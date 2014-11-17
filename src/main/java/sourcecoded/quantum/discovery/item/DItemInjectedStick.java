package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemInjectedStick extends DiscoveryItem{

    public DItemInjectedStick() {
        super("QA|Stick0");

        this.displayStack = new ItemStack(QAItems.INJECTED_STICK.getItem());

        this.x = 140;
        this.y = 80;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.JEWEL_CHARGED.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(displayStack)));
    }

}
