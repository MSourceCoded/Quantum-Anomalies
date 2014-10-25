package sourcecoded.quantum.discovery.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.crafting.vacuum.VacuumSyncCharged;
import sourcecoded.quantum.item.tools.ItemRiftBow;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

public class DItemLoreBasic extends DiscoveryItem {

    public DItemLoreBasic() {
        super("QA|Lore");

        this.displayStack = new ItemStack(Items.book);

        this.x = -10; this.y = 15;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));

        //TESTING
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), ArrangementRegistry.getRecipeForOutput(new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem()))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), InjectorRegistry.getRecipeForOutput(new ItemStack(QAItems.INJECTED_STRING.getItem()))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QABlocks.SYNC.getBlock(), 1, 1))));
    }


}
