package sourcecoded.quantum.registry;

import net.minecraft.item.Item;
import sourcecoded.core.item.AbstractItemRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;

public class ItemRegistry extends AbstractItemRegistry {

    public static ItemRegistry instance;
    public static ItemRegistry instance() {
        if (instance == null) instance = new ItemRegistry();
        return instance;
    }

    @Override
    public void addAll() {
        for (QAItems item : QAItems.values())
            addItem(item.getItemName(), item.getItem());
    }

    @Override
    public AbstractItemRegistry addItem(String name, Item item) {
        super.addItem(name, item);

        if (item instanceof IInjectorRecipe)
            InjectorRegistry.addRecipe((IInjectorRecipe) item);

        return this;
    }
}
