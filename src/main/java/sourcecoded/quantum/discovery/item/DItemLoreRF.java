package sourcecoded.quantum.discovery.item;

import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemLoreRF extends DiscoveryItem {

    public DItemLoreRF() {
        super("QA|RF");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/boltHelixVec.png");

        this.x = 100; this.y = 50;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(true);

        this.addParent(QADiscoveries.Item.LORE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
    }
}
