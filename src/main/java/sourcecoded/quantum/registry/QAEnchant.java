package sourcecoded.quantum.registry;

import net.minecraft.enchantment.Enchantment;
import sourcecoded.quantum.enchantment.EnchantmentDeception;
import sourcecoded.quantum.enchantment.EnchantmentQuantum;

public enum QAEnchant {
    DECEPTION(new EnchantmentDeception()),

    ;

    EnchantmentQuantum enchantment;

    QAEnchant(EnchantmentQuantum e) {
        this.enchantment = e;
    }

    public EnchantmentQuantum get() {
        return enchantment;
    }

    public static void register() {
        for (QAEnchant enchant : QAEnchant.values())
            Enchantment.addToBookList(enchant.get());
    }
}
