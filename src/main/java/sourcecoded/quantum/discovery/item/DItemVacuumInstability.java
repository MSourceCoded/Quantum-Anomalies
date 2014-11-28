package sourcecoded.quantum.discovery.item;

import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemVacuumInstability extends DiscoveryItem {

    public DItemVacuumInstability() {
        super("QA|Instability");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/instabilityVec.png");

        this.x = 180; this.y = 40;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.setSpecial(false);

        this.addParent(QADiscoveries.Item.VACUUM.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.3"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.4"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.5"));
    }
}