package sourcecoded.quantum.registry;

import net.minecraft.item.Item;
import sourcecoded.quantum.item.*;
import sourcecoded.quantum.item.armor.ItemRiftArmor;
import sourcecoded.quantum.item.tools.*;

public enum QAItems {

    SCEPTRE("itemSceptre", new ItemSceptre()),
    CHAOS_SHARD("itemChaosShard", new ItemChaosShard()),
    JOURNAL("itemJournal", new ItemJournal()),
    JOURNAL_CHEAT("itemJournalCheat", new ItemJournalCheatSheet()),
    INJECTED_STICK("itemInjectedStick", new ItemInjectedStick()),
    INJECTED_STRING("itemInjectedString", new ItemInjectedString()),

    OBSIDIAN_JEWEL("itemObsidianJewel", new ItemObsidianJewel()),
    ENTROPIC_STAR("itemEntropicStar", new ItemEntropicStar()),

    RIFT_HELM("riftArmor_helm", new ItemRiftArmor(0)),
    RIFT_CHEST("riftArmor_chest", new ItemRiftArmor(1)),
    RIFT_LEGS("riftArmor_legs", new ItemRiftArmor(2)),
    RIFT_BOOTS("riftArmor_boots", new ItemRiftArmor(3)),

    RIFT_AXE("itemRiftAxe", new ItemRiftAxe()),
    RIFT_PICKAXE("itemRiftPickaxe", new ItemRiftPickaxe()),
    RIFT_SHOVEL("itemRiftSpade", new ItemRiftShovel()),
    RIFT_SWORD("itemRiftSword", new ItemRiftSword()),

    RIFT_BOW("itemRiftBow", new ItemRiftBow()),
    RIFT_MAGNET("itemRiftMagnet", new ItemRiftMagnet()),

    PENCIL("itemRiftPencil", new ItemPencil()),
    ;

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
