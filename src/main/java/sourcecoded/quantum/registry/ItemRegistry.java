package sourcecoded.quantum.registry;

import sourcecoded.core.item.AbstractItemRegistry;
import sourcecoded.quantum.item.ItemCaosShard;
import sourcecoded.quantum.item.ItemSceptre;

public class ItemRegistry extends AbstractItemRegistry {

    public static ItemRegistry instance;
    public static ItemRegistry instance() {
        if (instance == null) instance = new ItemRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        addItem("itemSceptre", new ItemSceptre());

        addItem("itemCaosShard", new ItemCaosShard());
    }
}
