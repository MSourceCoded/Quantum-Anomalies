package sourcecoded.quantum.item;

import net.minecraft.item.Item;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.inventory.QATabs;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public abstract class ItemQuantum extends Item {

    public ItemQuantum() {
        this.setCreativeTab(QATabs.quantumTab);
    }

    public Item setTextureName(String name) {
        return super.setTextureName(prefix(Constants.MODID, name));
    }

    public Item setUnlocalizedName(String name) {
        return super.setUnlocalizedName(prefix(Constants.MODID_SHORT, name));
    }

}
