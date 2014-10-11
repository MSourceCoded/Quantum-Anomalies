package sourcecoded.quantum.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.handler.ConfigHandler;

public class EnchantmentQuantum extends Enchantment {

    public EnchantmentQuantum(ConfigHandler.Properties propertyID, String name, int weight, EnumEnchantmentType type) {
        super(ConfigHandler.getInteger(propertyID), weight, type);
        this.setName(name);
    }

    public String getName() {
        return "qa.enchantment." + this.name;
    }
}
