package sourcecoded.quantum.api.discovery;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An Item of Discovery. Displayed in
 * Discovery Categories.
 *
 * It is recommended that you extend this class
 * for you own research, however, you can reference
 * this in a new instance, as it is not abstract.
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

    /**
     * Is the item unlocked?
     */
    private boolean unlocked = false;

    /**
     * Should the item be rendered?
     */
    private boolean hidden = true;

    /**
     * An array of pages that this item
     * contains
     */
    public ArrayList<DiscoveryPage> pages;

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
        this.parents = new ArrayList<String>(Arrays.asList(parents));
        this.x = x;
        this.y = y;
        this.icon = icon;
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
        return "qa.research.item." + key + ".name";
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
        return StatCollector.translateToLocal(getUnlocalizedName());
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
     * Is this item unlocked?
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Is this item hidden?
     *
     * By default, this checks if the
     * item is set to be not hidden
     * (an override), and then
     * (if hidden) checks all the parents
     * if they are unlocked. If they are, it
     * returns false. Also returns
     * false if the item is unlocked
     *
     * Feel free to override this method
     */
    public boolean isHidden() {
        return !unlocked && hidden && !areAllParentsUnlocked();
    }

    /**
     * Set the hidden state of the
     * item.
     */
    public void setHidden(boolean val) {
        this.hidden = val;
    }

    /**
     * Checks if all the parents are unlocked
     */
    public boolean areAllParentsUnlocked() {
        for (String current : parents) {
            DiscoveryItem item = DiscoveryRegistry.getItemFromKey(current);
            if (!item.isUnlocked()) return false;
        }
        return true;
    }

    /**
     * Unlock all the parents
     */
    public void unlockAllParents() {
        this.unlocked = true;
        for (String current : parents) {
            DiscoveryItem item = DiscoveryRegistry.getItemFromKey(current);
            item.unlockAllParents();
        }
    }

    /**
     * Unlock the item.
     * @param force Should the unlock be forced?
     *              By default, the item will
     *              only unlock if all parents
     *              are unlocked, but if it's forced,
     *              it will unlock all parents as well.
     */
    public boolean unlock(boolean force) {
        if (areAllParentsUnlocked()) {
            this.unlocked = true;
            return true;
        } else if (force) {
            unlockAllParents();
            return true;
        }
        return false;
    }

}
