package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.registry.QAItems;

public class DItemToolArmor extends DiscoveryItem {
    public DItemToolArmor() {
        super("QA|Armor");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/armorVec.png");

        this.x = 110; this.y = 30;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(false);

        this.addParent(QADiscoveries.Item.TOOLS.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QAItems.RIFT_HELM.getItem()))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.2"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QAItems.RIFT_CHEST.getItem()))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.3"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QAItems.RIFT_LEGS.getItem()))));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.4"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), VacuumRegistry.getRecipeForOutput(new ItemStack(QAItems.RIFT_BOOTS.getItem()))));
    }
}
