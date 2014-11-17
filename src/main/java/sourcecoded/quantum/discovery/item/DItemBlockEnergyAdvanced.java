package sourcecoded.quantum.discovery.item;


import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemBlockEnergyAdvanced extends DiscoveryItem {

    public DItemBlockEnergyAdvanced() {
        super("QA|EnergyAdv");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/nodeAdvVec.png");

        this.x = 170;
        this.y = 0;

        this.addParent(QADiscoveries.Item.NODE.get().getKey());

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.3"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.4"));
    }
}