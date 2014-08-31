package sourcecoded.quantum.api.discovery;

import java.util.LinkedHashMap;

/**
 * The registry for everything to do with
 * the discovery mechanics of Quantum
 * Anomalies. Categories and their
 * respective Items are registered
 * in here. It is recommended you register
 * your items during Init.
 * @see cpw.mods.fml.common.event.FMLInitializationEvent
 */
public class DiscoveryRegistry {

    /**
     * A linked HashMap of all the categories
     *
     */
    public static LinkedHashMap<String, DiscoveryCategory> categories = new LinkedHashMap<String, DiscoveryCategory>();

    /**
     * Register your category in the registry.
     * Recommended you do this during Init.
     * @see cpw.mods.fml.common.event.FMLInitializationEvent
     */
    public static void registerCategory(DiscoveryCategory category) {
        categories.put(category.getKey(), category);
    }

    /**
     * Get a category in the registry.
     */
    public static DiscoveryCategory getCategory(String key) {
        return categories.get(key);
    }

    /**
     * Get the research item by its
     * identification key. Searches all
     * categories.
     *
     * @return The Item, null if nothing is found
     */
    public static DiscoveryItem getItemFromKey(String key) {
        for (DiscoveryCategory cat : categories.values()) {
            if (cat.hasDiscoveryItem(key))
                return cat.getDiscoveryItem(key);
        }

        return null;
    }

}
