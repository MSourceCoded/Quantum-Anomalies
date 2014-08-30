package sourcecoded.quantum.api.discovery;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
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

    private boolean isUnlocked = false;
    private boolean isHidden = false;

    /**
     * Create a new Discovery Category
     */
    public DiscoveryCategory(String key) {
        this.key = key;
    }

    /**
     * Set the icon of the Category
     */
    public void setIcon(ResourceLocation location) {
        this.icon = location;
    }

    /**
     * Set the background of the Category
     */
    public void setBackground(ResourceLocation location) {
        this.background = location;
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
    public void setKey(String newKey) {
        this.key = newKey;
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
    public void setUnlocked(boolean state) {
        this.isUnlocked = state;
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
    public void setHidden(boolean state) {
        this.isHidden = state;
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
    public void addDiscoveryItem(DiscoveryItem item) {
        discoveries.put(item.key, item);
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
