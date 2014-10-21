package sourcecoded.quantum.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.registry.QAItems;

public class EnchantmentRange extends EnchantmentQuantum {

    public EnchantmentRange() {
        super(ConfigHandler.Properties.ENCHANT_ID_RANGE, "range", 1, EnumEnchantmentType.all);
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean canApply(ItemStack stack) {
        return stack.getItem() == QAItems.RIFT_MAGNET.getItem() || stack.getItem().isItemTool(stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canApply(stack);
    }
}
