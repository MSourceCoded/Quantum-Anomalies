package sourcecoded.quantum.discovery.item;

import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.crafting.vacuum.VacuumEnchantDeception;
import sourcecoded.quantum.crafting.vacuum.VacuumEnchantRange;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemEnchantDeception extends DiscoveryItem {

    public DItemEnchantDeception() {
        super("QA|EnchantDeception");

        this.icon = new ResourceLocation(Constants.MODID, "textures/gui/deceptionVec.png");

        this.x = 270; this.y = 10;

        this.setHiddenByDefault(false);
        this.setUnlockedByDefault(false);

        this.setSpecial(false);

        this.addParent(QADiscoveries.Item.ENCHANT.get().getKey());

        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.0"));
        this.addPage(new DiscoveryPage(this.getUnlocalizedName(), this.getPrefixKey() + ".page.1"));
        for (IVacuumRecipe recipe : VacuumEnchantDeception.recipeList) {
            this.addPage(new DiscoveryPage(this.getUnlocalizedName(), recipe));
        }
    }
}