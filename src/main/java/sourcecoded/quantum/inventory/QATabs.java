package sourcecoded.quantum.inventory;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import sourcecoded.quantum.registry.QAItems;

public class QATabs {

    public static CreativeTabs quantumTab = new CreativeTabs("Quantum Anomalies") {
        @Override
        public Item getTabIconItem() {
            return QAItems.SCEPTRE.getItem();
        }
    };

}
