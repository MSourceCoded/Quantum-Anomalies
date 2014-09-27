package sourcecoded.quantum.registry;

import net.minecraft.item.Item;
import sourcecoded.quantum.item.*;

public enum QAItems {

    SCEPTRE("itemSceptre", new ItemSceptre()),
    OBSIDIAN_JEWEL("itemObsidianJewel", new ItemObsidianJewel()),
    CHAOS_SHARD("itemChaosShard", new ItemChaosShard()),
    JOURNAL("itemJournal", new ItemJournal()),
    INJECTED_STICK("itemInjectedStick", new ItemInjectedStick());

    String identifier;
    Item instance;

    QAItems(String identifier, Item instance) {
        this.identifier = identifier;
        this.instance = instance;
    }

    public String getItemName() {
        return identifier;
    }

    public Item getItem() {
        return instance;
    }

}
