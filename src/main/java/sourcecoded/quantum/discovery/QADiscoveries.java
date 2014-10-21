package sourcecoded.quantum.discovery;

import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.discovery.category.CategoryBasics;
import sourcecoded.quantum.discovery.category.CategoryBlocks;
import sourcecoded.quantum.discovery.category.CategoryItems;
import sourcecoded.quantum.discovery.category.CategoryTools;
import sourcecoded.quantum.discovery.item.DItemLoreBasic;
import sourcecoded.quantum.discovery.item.DItemLoreBiomes;

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
