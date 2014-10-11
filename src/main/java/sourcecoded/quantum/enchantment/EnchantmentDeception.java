package sourcecoded.quantum.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import sourcecoded.quantum.handler.ConfigHandler;

public class EnchantmentDeception extends EnchantmentQuantum {

    public EnchantmentDeception() {
        super(ConfigHandler.Properties.ENCHANT_ID_DECEPTION, "deception", 3, EnumEnchantmentType.armor);
    }

    public int getMaxLevel() {
        return 5;
    }

    public float baseChance() {
        return 0.05F;
    }

    public float chanceForLevel(int level) {
        return baseChance() * level;
    }
}
