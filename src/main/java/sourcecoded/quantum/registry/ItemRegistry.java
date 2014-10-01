package sourcecoded.quantum.registry;

import net.minecraft.item.Item;
import sourcecoded.core.item.AbstractItemRegistry;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;

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

        if (item instanceof IVacuumRecipe)
            VacuumRegistry.addRecipe((IVacuumRecipe) item);

        return this;
    }
}
