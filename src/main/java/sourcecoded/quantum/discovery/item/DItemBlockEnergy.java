package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockEnergy extends DiscoveryItem {

    public DItemBlockEnergy() {
        super("QA|Energy");

        //this.displayStack = new ItemStack(QABlocks.RIFT_NODE.getBlock());
        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/nodeVec.png");

        this.x = 100; this.y = 0;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), new ResourceLocation(Constants.MODID, "textures/discovery/rift_node.png"), this.getPrefixKey() + ".page.3"));
    }


}
