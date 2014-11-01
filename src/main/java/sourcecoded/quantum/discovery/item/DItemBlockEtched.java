package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QABlocks;

public class DItemBlockEtched extends DiscoveryItem {

    public DItemBlockEtched(int index) {
        super("QA|Etched" + index);

        this.x = 30;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.ARRANGEMENT.get().getKey());

        if (index == 0) {
            this.displayStack = new ItemStack(QABlocks.INJECTED_STONE.getBlock());
            this.y = 75;
        } else if (index == 1) {
            this.displayStack = new ItemStack(QABlocks.INJECTED_CORNERSTONE.getBlock());
            this.y = 110;
        } else
            throw new IndexOutOfBoundsException("Etched Discovery Entry: " + index + " Out of bounds!");

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(displayStack)));

        if (index == 1)
            this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(new ItemStack(Blocks.stonebrick, 1, 3))));
    }
}
