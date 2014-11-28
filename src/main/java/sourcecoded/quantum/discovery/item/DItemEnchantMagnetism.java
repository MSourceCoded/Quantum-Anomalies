package sourcecoded.quantum.discovery.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.crafting.vacuum.VacuumEnchantRange;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemEnchantMagnetism extends DiscoveryItem {

    public DItemEnchantMagnetism() {
        super("QA|EnchantMagnet");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/magnetismVec.png");

        this.x = 210; this.y = 10;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(false);

        this.addParent(QADiscoveries.Item.ENCHANT.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        for (IVacuumRecipe recipe : VacuumEnchantRange.recipeList) {
            this.addPage(new DiscoveryPage(this.getUnlocalizedName(), recipe));
        }
    }
}