package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemTools extends DiscoveryItem {

    public static String[] types = {"Pickaxe", "Axe", "Shovel", "Sword", "Bow"};

    public DItemTools(int type) {
        super("QA|" + types[type]);

        ItemStack stack = null;

        if (type == 0) stack = new ItemStack(QAItems.RIFT_PICKAXE.getItem());
        if (type == 1) stack = new ItemStack(QAItems.RIFT_AXE.getItem());
        if (type == 2) stack = new ItemStack(QAItems.RIFT_SHOVEL.getItem());
        if (type == 3) stack = new ItemStack(QAItems.RIFT_SWORD.getItem());
        if (type == 4) stack = new ItemStack(QAItems.RIFT_BOW.getItem());

        this.displayStack = stack;

        this.x = 10; this.y = -30 + (30 * type);

        this.addParent(QADiscoveries.Item.TOOLS.get().getKey());

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(stack)));
    }

}
