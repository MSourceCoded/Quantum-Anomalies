package sourcecoded.quantum.api.discovery;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.LinkedHashMap;

/**
 * Discovery Categories are chapters of the
 * In-Game manual. It is recommended that you
 * create a new one if you are hooking into this
 * for an add-on mod.
 */
public class DiscoveryCategory {

    public LinkedHashMap<String, DiscoveryItem> discoveries = new LinkedHashMap<String, DiscoveryItem>();
    private String key;

    public ResourceLocation icon;
    public ResourceLocation background;
    public ItemStack displayStack;

    private boolean isUnlocked = false;
    private boolean isHidden = false;

    /**
     * Create a new Discovery Category.
     *
     * Names beginning with "QA|" are reserved
     */
    public DiscoveryCategory(String key) {
        this.key = key;
    }

    /**
     * Set the icon of the Category
     */
    public DiscoveryCategory setIcon(ResourceLocation location) {
        this.icon = location;
        return this;
    }

    /**
     * Set the background of the Category
     */
    public DiscoveryCategory setBackground(ResourceLocation location) {
        this.background = location;
        return this;
    }

    /**
     * Set the icon for the Category in an ItemStack form
     */
    public DiscoveryCategory setDisplayItemStack(ItemStack stack) {
        this.displayStack = stack;
        return this;
    }

    /**
     * Get the identifying key of the Category.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the identifying key of the Category.
     */
    public DiscoveryCategory setKey(String newKey) {
        this.key = newKey;
        return this;
    }

    /**
     * Get the Unlocalized name for the
     * category (before StatCollector/l18n)
     */
    public String getUnlocalizedName() {
        return "qa.research.category." + key + ".name";
    }

    /**
     * Get the Localized name for the
     * category (after StatCollector/l18n)
     */
    public String getLocalizedName() {
        return StatCollector.translateToLocal(getUnlocalizedName());
    }

    /**
     * Set if the Category is 'unlocked' (accessible)
     */
    public DiscoveryCategory setUnlocked(boolean state) {
        this.isUnlocked = state;
        return this;
    }

    /**
     * Get if the Category is 'unlocked' (accessible)
     */
    public boolean getUnlocked() {
        return isUnlocked;
    }

    /**
     * Set the Category's hidden state (can be seen)
     */
    public DiscoveryCategory setHidden(boolean state) {
        this.isHidden = state;
        return this;
    }

    /**
     * Get the Category's hidden state (can be seen)
     */
    public boolean getHidden() {
        return isHidden;
    }

    /**
     * Add a discovery item to this category
     */
    public DiscoveryCategory addDiscoveryItem(DiscoveryItem item) {
        discoveries.put(item.key, item);
        return this;
    }

    /**
     * Does this category withhold this item?
     */
    public boolean hasDiscoveryItem(String key) {
        return discoveries.containsKey(key);
    }

    /**
     * Get a discovery item by it's key
     */
    public DiscoveryItem getDiscoveryItem(String key) {
        return discoveries.get(key);
    }
}
