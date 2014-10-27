package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;

public class DItemLoreBasic extends DiscoveryItem {

    public DItemLoreBasic() {
        super("QA|Lore");

        //this.displayStack = new ItemStack(Items.book);
        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/quantumVec.png");

        this.x = 100; this.y = 0;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
    }


}
