package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockArrangement extends DiscoveryItem {

    public DItemBlockArrangement() {
        super("QA|Arrangement");

        this.displayStack = new ItemStack(QABlocks.ARRANGEMENT.getBlock());

        this.x = 60; this.y = 40;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.NODE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getPrefixKey() + ".page.2.title", new ResourceLocation(Constants.MODID, "textures/discovery/arrangementMulti.png"), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.3"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.4"));
        this.addPage(new DiscoveryPage(this.getPrefixKey() + ".page.5.title", new ResourceLocation(Constants.MODID, "textures/discovery/arrangementExample.png"), this.getPrefixKey() + ".page.5"));
    }
}
