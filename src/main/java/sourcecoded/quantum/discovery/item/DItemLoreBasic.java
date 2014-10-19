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
import sourcecoded.quantum.registry.QAItems;

public class DItemLoreBasic extends DiscoveryItem {

    public DItemLoreBasic() {
        super("QA|Lore");

        this.displayStack = new ItemStack(Items.book);

        this.x = -10; this.y = 15;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(true);

        this.addPage(new DiscoveryPage("Discovery Page Demo", "Welcome to the Demonstration page of the {c:DARK_PURPLE}Anomolical Journal{c:BLACK}. Please keep your hands and legs inside of the vehicle at all times.<br>What a {c:ITALIC}magical{c:RESET} book, I'm sure it holds all kinds of secrets.<br>Please help I'm {c:STRIKETHROUGH}trapped in here{c:RESET} {c:DARK_RED}{c:ITALIC}{c:BOLD} SHUT UP!"));
        this.addPage(new DiscoveryPage("Crafting Arrangement", ArrangementRegistry.getRecipeForOutput(new ItemStack(QAItems.RIFT_PICKAXE.getItem()))));
        this.addPage(new DiscoveryPage("Crafting Injection", InjectorRegistry.getRecipeForOutput(new ItemStack(QAItems.OBSIDIAN_JEWEL.getItem(), 1, 1))));
        this.addPage(new DiscoveryPage("Crafting Vacuum", new VacuumSyncCharged()));
    }


}
