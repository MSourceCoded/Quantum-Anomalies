package sourcecoded.quantum.api.discovery;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Discovery Categories are chapters of the
 * In-Game manual. It is recommended that you
 * create a new one if you are hooking into this
 * for an add-on mod.
 *
 * @author SourceCoded
 */
public class DiscoveryCategory {

    public LinkedHashMap<String, DiscoveryItem> discoveries = new LinkedHashMap<String, DiscoveryItem>();
    private String key;

    public ResourceLocation icon;
    public ResourceLocation background;
    public ItemStack displayStack;

    public boolean hiddenByDefault = true;
    public boolean unlockedByDefault = false;

    public Colourizer titleColour = Colourizer.LIGHT_GRAY;
    public float titleScale = 0.5F;

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
        return "qa.journal.category." + key + ".name";
    }

    /**
     * Get the Localized name for the
     * category (after StatCollector/l18n)
     */
    public String getLocalizedName() {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(), getUnlocalizedName());
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

    /**
     * Set the title scale for the Journal
     */
    public DiscoveryCategory setTitleScale(float f) {
        this.titleScale = f;
        return this;
    }

    /**
     * Set the title colour for the journal
     */
    public DiscoveryCategory setTitleColour(Colourizer colour) {
        this.titleColour = colour;
        return this;
    }

    /**
     * Set the title scale for the Journal
     */
    public float getTitleScale() {
        return this.titleScale;
    }

    /**
     * Get the title colour for the journal. This should
     * usually be WHITE if you plan to change the colour
     * in the lang file
     */
    public Colourizer getTitleColour() {
        return this.titleColour;
    }

    /**
     * Change the category's default unlock state
     */
    public DiscoveryCategory setUnlockedByDefault(boolean state) {
        this.unlockedByDefault = state;
        return this;
    }

    /**
     * Change the category's default hidden state
     */
    public DiscoveryCategory setHiddenByDefault(boolean state) {
        this.hiddenByDefault = state;
        return this;
    }
}
