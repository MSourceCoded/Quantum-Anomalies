package sourcecoded.quantum.api.discovery;

import java.util.LinkedHashMap;

public class DiscoveryRegistry {

    public static LinkedHashMap<String, DiscoveryCategory> categories = new LinkedHashMap<String, DiscoveryCategory>();

    public static void registerCategory(DiscoveryCategory category) {
        categories.put(category.getKey(), category);
    }

    public static DiscoveryCategory getCategory(String key) {
        return categories.get(key);
    }

    public static DiscoveryItem getItemFromKey(String key) {
        for (DiscoveryCategory cat : categories.values()) {
            if (cat.hasDiscoveryItem(key))
                return cat.getDiscoveryItem(key);
        }

        return null;
    }

}
