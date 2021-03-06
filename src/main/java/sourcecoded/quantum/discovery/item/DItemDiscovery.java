package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemDiscovery extends DiscoveryItem {

    public DItemDiscovery() {
        super("QA|Discovery");

        //this.displayStack = new ItemStack(Items.paper);

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/discoveryVec.png");

        this.x = 150; this.y = 35;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.LORE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
    }

}
