package sourcecoded.quantum.discovery;

import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.discovery.category.CategoryBasics;
import sourcecoded.quantum.discovery.category.CategoryBlocks;
import sourcecoded.quantum.discovery.category.CategoryItems;
import sourcecoded.quantum.discovery.category.CategoryTools;
import sourcecoded.quantum.discovery.item.*;

public class QADiscoveries {

    public static enum Category {
        BASICS(new CategoryBasics()),
        BLOCKS(new CategoryBlocks()),
        ITEMS(new CategoryItems()),
        TOOLS(new CategoryTools()),

        ;

        private DiscoveryCategory category;

        Category(DiscoveryCategory cat) {
            this.category = cat;
        }

        public DiscoveryCategory get() {
            return category;
        }
    }

    public static enum Item {
        LORE(new DItemLoreBasic(), Category.BASICS.get()),
        BIOME(new DItemLoreBiomes(), Category.BASICS.get()),
        DISCOVERY(new DItemDiscovery(), Category.BASICS.get()),
        RF(new DItemLoreRF(), Category.BASICS.get()),
        POTATO(new DItemLorePotato(), Category.BASICS.get()),

        DYEABLES(new DItemBlockDyeable(), Category.BLOCKS.get()),

        NODE(new DItemBlockEnergy(), Category.BLOCKS.get()),
        NODE_ADV(new DItemBlockEnergyAdvanced(), Category.BLOCKS.get()),
        ARRANGEMENT(new DItemBlockArrangement(), Category.BLOCKS.get()),

        JEWEL(new DItemJewel(), Category.ITEMS.get()),
        JEWEL_CHARGED(new DItemJewelCharged(), Category.ITEMS.get()),
        STRING(new DItemInjectedString(), Category.ITEMS.get()),
        STICK(new DItemInjectedStick(), Category.ITEMS.get()),

        ETCHED_STONE(new DItemBlockEtched(0), Category.BLOCKS.get()),
        ETCHED_CORNER(new DItemBlockEtched(1), Category.BLOCKS.get()),

        INJECTION(new DItemBlockInjector(), Category.BLOCKS.get()),
        INJECTION_BLOCKS(new DItemBlockInjected(), Category.BLOCKS.get()),
        INJECTION_ADV(new DItemBlockInjectorAdvanced(), Category.BLOCKS.get()),

        FURNACE(new DItemBlockRiftFurnace(), Category.BLOCKS.get()),
        CHAOS(new DItemBlockChaos(), Category.BLOCKS.get()),
        SHELF(new DItemBlockShelf(), Category.BLOCKS.get()),

        VACUUM(new DItemVacuum(), Category.BLOCKS.get()),
        VACUUM_INSTABILITY(new DItemVacuumInstability(), Category.BLOCKS.get()),
        ENCHANT(new DItemVacuumEnchant(), Category.BLOCKS.get()),
        MANIPULATION(new DItemBlockManipulation(), Category.BLOCKS.get()),
        PLAYER(new DItemBlockPlayer(), Category.BLOCKS.get()),

        STAR(new DItemEntropicStar(), Category.ITEMS.get()),
        MAGNET(new DItemMagnet(), Category.ITEMS.get()),

        MAGNETISM(new DItemEnchantMagnetism(), Category.BLOCKS.get()),
        DECEPTION(new DItemEnchantDeception(), Category.BLOCKS.get()),  //This

        SYNC(new DItemBlockSync(), Category.BLOCKS.get()),

        TOOLS(new DItemToolRod(), Category.TOOLS.get()),
        PICKAXE(new DItemTools(0), Category.TOOLS.get()),
        AXE(new DItemTools(1), Category.TOOLS.get()),
        SHOVEL(new DItemTools(2), Category.TOOLS.get()),
        SWORD(new DItemTools(3), Category.TOOLS.get()),
        BOW(new DItemTools(4), Category.TOOLS.get()),
        ARMOR(new DItemToolArmor(), Category.TOOLS.get()),

        PENCIL(new DItemToolPencil(), Category.TOOLS.get()),
        ;

        private DiscoveryItem item;
        private DiscoveryCategory category;

        Item(DiscoveryItem cat, DiscoveryCategory category) {
            this.item = cat;
            this.category = category;
        }

        public DiscoveryItem get() {
            return item;
        }

        public DiscoveryCategory getCategory() {
            return category;
        }

        public void registerItem() {
            category.addDiscoveryItem(item);
            item.init();
        }
    }

}
