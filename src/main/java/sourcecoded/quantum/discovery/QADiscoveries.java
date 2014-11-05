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

        DYEABLES(new DItemBlockDyeable(), Category.BLOCKS.get()),

        NODE(new DItemBlockEnergy(), Category.BLOCKS.get()),
        ARRANGEMENT(new DItemBlockArrangement(), Category.BLOCKS.get()),

        JEWEL(new DItemJewel(), Category.ITEMS.get()),

        ETCHED_STONE(new DItemBlockEtched(0), Category.BLOCKS.get()),
        ETCHED_CORNER(new DItemBlockEtched(1), Category.BLOCKS.get()),

        INJECTION(new DItemBlockInjector(), Category.BLOCKS.get()),
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
        }
    }

}
