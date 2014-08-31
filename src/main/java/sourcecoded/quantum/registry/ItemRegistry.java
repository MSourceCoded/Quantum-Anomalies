package sourcecoded.quantum.registry;

import net.minecraft.item.Item;
import sourcecoded.core.item.AbstractItemRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.item.ItemChaosShard;
import sourcecoded.quantum.item.ItemInfusedStick;
import sourcecoded.quantum.item.ItemJournal;
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

        addItem("itemJournal", new ItemJournal());

        addItem("itemChaosShard", new ItemChaosShard());
        addItem("itemInfusedStick", new ItemInfusedStick());
    }

    @Override
    public void addItem(String name, Item item) {
        super.addItem(name, item);

        if (item instanceof IInjectorRecipe)
            InjectorRegistry.addRecipe((IInjectorRecipe) item);
    }
}
