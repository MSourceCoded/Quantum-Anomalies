package sourcecoded.quantum;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import sourcecoded.quantum.registry.QABlocks;

public class Achievements {

    /* Achievements */
    public static Achievement learning = new Achievement("learning", "qa|learning", 0, 0, QABlocks.CHAOS_ENDER.getBlock(), null).setSpecial().registerStat();
    public static Achievement secret = new Achievement("secret", "qa|secret", 2, 4, QABlocks.CHAOS_HELL.getBlock(), null).setSpecial().registerStat();

    /* Pages */
    public static AchievementPage page = new AchievementPage("Quantum Anomalies");

    public static void initAchievements() {
        AchievementPage.registerAchievementPage(page);
        page.getAchievements().add(learning);
        page.getAchievements().add(secret);
    }

}
