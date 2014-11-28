package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemToolPencil extends DiscoveryItem {
    public DItemToolPencil() {
        super("QA|Pencil");

        this.displayStack = new ItemStack(QAItems.PENCIL.getItem());

        this.x = 110; this.y = 0;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.TOOLS.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), new ResourceLocation(Constants.MODID, "textures/discovery/iamblock.png"), this.getPrefixKey() + ".page.1"));
    }
}
