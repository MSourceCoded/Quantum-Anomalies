package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.crafting.vacuum.VacuumEnchantDeception;
import sourcecoded.quantum.registry.QAItems;

public class DItemToolRod extends DiscoveryItem {
    public DItemToolRod() {
        super("QA|Rod");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/toolsVec.png");

        this.x = 60; this.y = 30;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(true);

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1))));
    }
}
