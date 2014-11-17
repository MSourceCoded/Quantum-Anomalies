package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemVacuum extends DiscoveryItem {

    public DItemVacuum() {
        super("QA|Vacuum");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/riftTearVec.png");

        this.x = 140; this.y = 40;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addParent(QADiscoveries.Item.NODE.get().getKey());
        this.addParent(QADiscoveries.Item.NODE_ADV.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.2"));

        //Image

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.4"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.5"));
    }
}