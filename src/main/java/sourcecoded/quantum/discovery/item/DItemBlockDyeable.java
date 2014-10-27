package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;

public class DItemBlockDyeable extends DiscoveryItem {

    public DItemBlockDyeable() {
        super("QA|Dyeable");

        this.x = 30; this.y = 0;

        this.displayStack = new ItemStack(Items.dye, 1, 14);

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), new ResourceLocation(Constants.MODID, "textures/discovery/dyeables.png"), this.getPrefixKey() + ".page.1"));
    }

}
