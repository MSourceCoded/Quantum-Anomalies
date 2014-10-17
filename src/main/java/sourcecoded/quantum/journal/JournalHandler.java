package sourcecoded.quantum.journal;

import sourcecoded.quantum.api.discovery.DiscoveryRegistry;
import sourcecoded.quantum.journal.category.CategoryBasics;

public class JournalHandler {

    public static void init() {
        CategoryBasics basicNotYetUnlocked = new CategoryBasics(2);
        basicNotYetUnlocked.setUnlocked(false);

        DiscoveryRegistry.registerCategory(new CategoryBasics(0));
        DiscoveryRegistry.registerCategory(new CategoryBasics(1));
        DiscoveryRegistry.registerCategory(basicNotYetUnlocked);
        DiscoveryRegistry.registerCategory(new CategoryBasics(3));
        DiscoveryRegistry.registerCategory(new CategoryBasics(4));
        DiscoveryRegistry.registerCategory(new CategoryBasics(5));
        DiscoveryRegistry.registerCategory(new CategoryBasics(6));
    }

}
