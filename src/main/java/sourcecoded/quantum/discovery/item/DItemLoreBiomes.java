package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemLoreBiomes extends DiscoveryItem {

    public DItemLoreBiomes() {
        super("QA|Biome");

        this.displayStack = new ItemStack(Items.filled_map);

        this.x = 50; this.y = 35;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.LORE.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), new ResourceLocation(Constants.MODID, "textures/discovery/biome_anomaly_end.png"), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), new ResourceLocation(Constants.MODID, "textures/discovery/biome_anomaly_hell.png"), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getPrefixKey() + ".page.3.t", this.getPrefixKey() + ".page.3"));
    }


}
