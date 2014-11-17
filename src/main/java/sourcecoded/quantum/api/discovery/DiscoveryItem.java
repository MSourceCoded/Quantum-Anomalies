package sourcecoded.quantum.api.discovery;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An Item of Discovery. Displayed in
 * Discovery Categories.
 *
 * It is recommended that you extend this class
 * for you own research, however, you can reference
 * this in a new instance, as it is not abstract.
 *
 * @author SourceCoded
 */
public class DiscoveryItem {

    /**
     * identification Key.
     * Appended with .name for the name
     * Appended with .desc for the description
     */
    public String key;

    /**
     * A list of the Parent research required
     */
    public ArrayList<String> parents = new ArrayList<String>();

    /**
     * The 'x' location of the icon on the GUI
     */
    public int x;

    /**
     * The 'y' location of the icon on the GUI
     */
    public int y;

    /**
     * The icon to display on the GUI
     */
    public ResourceLocation icon;

    public ItemStack displayStack;

    /**
     * An array of pages that this item
     * contains
     */
    public ArrayList<DiscoveryPage> pages;

    /**
     * Is this item hidden by default?
     */
    public boolean hiddenByDefault = true;

    /**
     * Is this item unlocked by default?
     */
    public boolean unlockedByDefault = false;

    /**
     * Is this a special icon?
     */
    public boolean isSpecial = false;

    /**
     * Parent line colour
     */
    public Colourizer parentLineColour = Colourizer.PINK;

    /**
     * Child line colour
     */
    public Colourizer childLineColour = Colourizer.PURPLE;

    /**
     * Create a new DiscoveryItem
     * @param key The identification key
     */
    public DiscoveryItem(String key) {
        this(key, null, 0, 0, null);
    }

    /**
     * Create a new DiscoveryItem
     * @param key The identification key
     * @param parents The parent discoveries
     *                required
     * @param x The x location to render at
     *          on the category
     * @param y The y location to render at
     *          on the category
     * @param icon The icon for the Item
     */
    public DiscoveryItem(String key, String[] parents, int x, int y, ResourceLocation icon) {
        this.key = key;
        if (parents != null)
            this.parents = new ArrayList<String>(Arrays.asList(parents));
        this.x = x;
        this.y = y;
        this.icon = icon;

        this.pages = new ArrayList<DiscoveryPage>();
    }

    /**
     * Get the Identification key for
     * the item
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the Unlocalized name for the
     * item (before StatCollector/l18n).
     *
     * Feel free to Override this. It's
     * only for Translation
     */
    public String getUnlocalizedName() {
        return "qa.journal.item." + key + ".name";
    }

    /**
     * Similar to #getUnlocalizedName, but without the
     * .name suffix
     */
    public String getPrefixKey() {
        return "qa.journal.item." + key;
    }

    /**
     * Get the Unlocalized Name for the
     * item's Description
     * (before StatCollector/l18n)
     */
    public String getUnlocalizedDescription() {
        return "qa.journal.item." + key + ".desc";
    }

    /**
     * Get the Localized name for the
     * item (before StatCollector/l18n)
     *
     * I wouldn't override this, as it's
     * only for translating the unlocalized
     * name. But hey, I'm a comment,
     * not a cop
     */
    public String getLocalizedName() {
        return LocalizationUtils.translateLocalWithColours(getUnlocalizedName(), getUnlocalizedName());
    }

    public void init() {
        if (isSpecial) {
            this.childLineColour = Colourizer.RED;
        }
    }

    /**
     * Set the colour to attach to parents with
     */
    public DiscoveryItem setParentColour(Colourizer colour) {
        this.parentLineColour = colour;
        return this;
    }

    /**
     * Set the colour to attach to children with
     */
    public DiscoveryItem setChildColour(Colourizer colour) {
        this.childLineColour = colour;
        return this;
    }

    /**
     * Get the colour to attach to parents with
     */
    public Colourizer getParentColour() {
        return parentLineColour;
    }

    /**
     * Get the colour to attach to children with
     */
    public Colourizer getChildColour() {
        return childLineColour;
    }

    /**
     * Add a page to the item. Returns itself
     * for chaining.
     */
    public DiscoveryItem addPage(DiscoveryPage page) {
        this.pages.add(page);
        return this;
    }

    /**
     * Get the arrayList of all the pages
     * for this discovery
     */
    public ArrayList<DiscoveryPage> getPages() {
        return pages;
    }

    /**
     * Add a new parent to the item. Returns itself
     * for chaining.
     */
    public DiscoveryItem addParent(String key) {
        this.parents.add(key);
        return this;
    }

    /**
     * Set the item to have a special icon
     */
    public DiscoveryItem setSpecial(boolean state) {
        this.isSpecial = state;
        return this;
    }

    /**
     * Get if the item has a special state
     */
    public boolean getSpecial() {
        return isSpecial;
    }

    /**
     * Set the icon for the Category in an ItemStack form
     */
    public DiscoveryItem setDisplayItemStack(ItemStack stack) {
        this.displayStack = stack;
        return this;
    }

    /**
     * Get the 'x' column to render at
     */
    public int getX() {
        return x;
    }

    /**
     * Get the 'y' row to render at
     */
    public int getY() {
        return y;
    }

    /**
     * Get the icon to render
     */
    public ResourceLocation getIcon() {
        return icon;
    }

    /**
     * Change the item's default unlock state
     */
    public DiscoveryItem setUnlockedByDefault(boolean state) {
        this.unlockedByDefault = state;
        return this;
    }

    /**
     * Change the item's default hidden state
     */
    public DiscoveryItem setHiddenByDefault(boolean state) {
        this.hiddenByDefault = state;
        return this;
    }

}
