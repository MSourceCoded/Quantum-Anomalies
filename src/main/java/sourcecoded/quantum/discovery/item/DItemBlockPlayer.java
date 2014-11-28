package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockPlayer extends DiscoveryItem{

    public DItemBlockPlayer() {
        super("QA|Player");

        this.displayStack = new ItemStack(QABlocks.PLAYER.getBlock());

        this.x = 180; this.y = 100;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(false);

        this.addParent(QADiscoveries.Item.MANIPULATION.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(displayStack)));
    }

}