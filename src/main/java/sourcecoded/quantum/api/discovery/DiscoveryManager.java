package sourcecoded.quantum.api.discovery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.event.discovery.DiscoveryUpdateEvent;

import java.util.List;
import java.util.Map;

/**
 * Use this to unlock discoveries, as it works with the Player's NBT
 *
 * @author SourceCoded
 */
public class DiscoveryManager {

    /**
     * Gets and initializes a category if it has not been initialized
     * already
     */
    public static NBTTagCompound getCategory(String category, EntityPlayer player) {
        NBTTagCompound discoveries = getDiscoveriesTag(player);

        DiscoveryCategory cat = DiscoveryRegistry.getCategory(category);
        if (cat == null) return null;

        if (!discoveries.hasKey(category)) {
            discoveries.setTag(category, new NBTTagCompound());
        }

        NBTTagCompound categoryTags = discoveries.getCompoundTag(category);
        if (!categoryTags.hasKey("Unlocked"))
            categoryTags.setBoolean("Unlocked", cat.unlockedByDefault);
        if (!categoryTags.hasKey("Hidden"))
            categoryTags.setBoolean("Hidden", cat.hiddenByDefault);
        if (!categoryTags.hasKey("Items"))
            categoryTags.setTag("Items", new NBTTagCompound());

        return categoryTags;
    }

    /**
     * Gets and initializes an item if it has not been initialized
     * already
     */
    public static NBTTagCompound getItem(String item, EntityPlayer player) {
        DiscoveryCategory category = DiscoveryRegistry.getCategoryForItem(item);
        if (category == null) return null;

        DiscoveryItem it = DiscoveryRegistry.getItemFromKey(item);

        if (it == null) return null;

        NBTTagCompound cat = getCategory(category.getKey(), player);
        NBTTagCompound items = cat.getCompoundTag("Items");

        if (!items.hasKey(item))
            items.setTag(item, new NBTTagCompound());

        if (!items.hasKey("Unlocked"))
            items.setBoolean("Unlocked", it.unlockedByDefault);
        if (!items.hasKey("Hidden"))
            items.setBoolean("Hidden", it.hiddenByDefault);

        return items;
    }

    /**
     * Initializes the categories and items
     */
    public static void init(EntityPlayer player) {
        for (Map.Entry<String, DiscoveryCategory> entry : DiscoveryRegistry.categories.entrySet())
            getCategory(entry.getKey(), player);
    }

    /**
     * Set a category as unlocked (discovered)
     */
    public static void unlockCategory(String category, EntityPlayer player) {
        NBTTagCompound compound = getCategory(category, player);
        compound.setBoolean("Unlocked", true);
        revealCategory(category, player);
        sync(player);
    }

    /**
     * Set an item as unlocked (discovered)
     */
    public static void unlockItem(String item, EntityPlayer player, boolean force) {
        if (!areParentsUnlocked(item, player)) {
            if (force) {
                unlockSingleItem(item, player);

                List<String> items = DiscoveryRegistry.getItemFromKey(item).parents;
                for (String itemN : items)
                    unlockItem(itemN, player, true);
            }
        } else
            unlockSingleItem(item, player);

        sync(player);
    }

    static void unlockSingleItem(String item, EntityPlayer player) {
        getItem(item, player).setBoolean("Unlocked", true);
        getItem(item, player).setBoolean("Hidden", false);

        unlockCategory(DiscoveryRegistry.getCategoryForItem(item).getKey(), player);
    }

    /**
     * Set a category as revealed (not hidden)
     */
    public static void revealCategory(String category, EntityPlayer player) {
        getCategory(category, player).setBoolean("Hidden", false);

        sync(player);
    }

    /**
     * Set an item as revealed (not hidden)
     */
    public static void revealItem(String item, EntityPlayer player) {
        getItem(item, player).setBoolean("Hidden", false);

        sync(player);
    }

    /**
     * Get the 'hidden' state of an item
     */
    public static boolean itemHidden(String item, EntityPlayer player) {
        return getItem(item, player).getBoolean("Hidden");
    }

    /**
     * Get the 'hidden' state of a category
     */
    public static boolean categoryHidden(String category, EntityPlayer player) {
        return getCategory(category, player).getBoolean("Hidden");
    }

    /**
     * Get the 'unlocked' state of an item
     */
    public static boolean itemUnlocked(String item, EntityPlayer player) {
        return getItem(item, player).getBoolean("Unlocked");
    }

    /**
     * Get the 'unlocked' state of a category
     */
    public static boolean categoryUnlocked(String category, EntityPlayer player) {
        return getCategory(category, player).getBoolean("Unlocked");
    }

    public static boolean areParentsUnlocked(String item, EntityPlayer player) {
        List<String> items = DiscoveryRegistry.getItemFromKey(item).parents;
        for (String itemN : items) {
            if (!itemUnlocked(item, player)) return false;
        }

        return true;
    }

    public static NBTTagCompound getDiscoveriesTag(EntityPlayer player) {
        NBTTagCompound persist = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        if (persist == null || !player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            persist = new NBTTagCompound();
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persist);
        }

        NBTTagCompound qa = new NBTTagCompound();
        if (persist.hasKey("QuantumAnomalies"))
            qa = persist.getCompoundTag("QuantumAnomalies");
        else
            persist.setTag("QuantumAnomalies", qa);

        NBTTagCompound disc = new NBTTagCompound();
        if (qa.hasKey("discoveries"))
            disc = qa.getCompoundTag("discoveries");
        else
            qa.setTag("discoveries", disc);

        return disc;
    }

    /**
     * Sync the changes between client/server
     */
    public static void sync(EntityPlayer player) {
        QuantumAPI.eventBus.post(new DiscoveryUpdateEvent(player));
    }
}
