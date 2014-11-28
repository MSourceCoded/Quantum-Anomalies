package sourcecoded.quantum.discovery.item;

import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemVacuumEnchant extends DiscoveryItem {

    public DItemVacuumEnchant() {
        super("QA|Enchant");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/enchantVec.png");

        this.x = 240; this.y = 40;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addParent(QADiscoveries.Item.VACUUM_INSTABILITY.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
    }
}