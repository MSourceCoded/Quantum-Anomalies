package sourcecoded.quantum;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import sourcecoded.quantum.registry.BlockRegistry;
import sourcecoded.quantum.registry.ItemRegistry;

public class Achievements {

    /* Achievements */
    public static Achievement secret = new Achievement("secret", "qa|secret", 0, 0, BlockRegistry.instance().getBlockByName("blockChaosHell"), null).setSpecial().registerStat();

    /* Pages */
    public static AchievementPage page = new AchievementPage("Quantum Anomalies");

    public static void initAchievements() {
        AchievementPage.registerAchievementPage(page);
        page.getAchievements().add(secret);
    }

}
