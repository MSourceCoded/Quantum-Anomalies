package sourcecoded.quantum.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.registry.QAItems;

public class EnchantmentRange extends EnchantmentQuantum {

    public EnchantmentRange() {
        super(ConfigHandler.Properties.ENCHANT_ID_RANGE, "range", 2, EnumEnchantmentType.all);
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean canApply(ItemStack stack) {
        return stack.getItem() == QAItems.RIFT_MAGNET.getItem();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canApply(stack);
    }

    public int getMinEnchantability(int l) {
        return 1 + l * 10;
    }

    public int getMaxEnchantability(int l) {
        return getMinEnchantability(l) + 5;
    }
}
