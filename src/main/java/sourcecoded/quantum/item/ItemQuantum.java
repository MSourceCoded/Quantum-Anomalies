package sourcecoded.quantum.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.inventory.QATabs;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public abstract class ItemQuantum extends Item {

    public String customName;

    public ItemQuantum() {
        this.setCreativeTab(QATabs.quantumTab);
    }

    public Item setTextureName(String name) {
        return super.setTextureName(prefix(Constants.MODID, name));
    }

    public Item setUnlocalizedName(String name) {
        customName = name;
        return super.setUnlocalizedName(prefix(Constants.MODID_SHORT, name));
    }

    public String getUnlocalizedName(ItemStack item) {
        return "qa.items." + customName;
    }

    public String getItemStackDisplayName(ItemStack item) {
        return StatCollector.translateToLocal(getUnlocalizedName(item) + ".name");
    }

}
