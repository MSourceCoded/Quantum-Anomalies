package sourcecoded.quantum.api.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.util.List;

/**
 * Utilities in relation to Rift Multipliers, mostly used for Lore.
 *
 * @author SourceCoded
 */
public class RiftMultiplierUtils {

    /**
     * Get the lore contents of the Rift Multiplier
     */
    public static String getLore(IRiftMultiplier.RiftMultiplierTypes type, Block block) {
        float data = ((IRiftMultiplier) block).getRiftMultiplication(type);

        if (data < 1F) return type.negative + "-" + Math.round((1F - data) * 100) + "%";
        if (data > 1F) return type.positive + "+" + Math.round((data - 1F) * 100) + "%";
        return EnumChatFormatting.GOLD + "N/A";
    }

    /**
     * Add the Lore automatically. Uses en_US defaults
     * if the .lang file is not present (QA not loaded)
     */
    @SuppressWarnings("unchecked")
    public static void addLoreToItemBlock(ItemBlock itemblock, List loreList) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            loreList.add(translate("qa.lore.multipliers.speed", "Speed Multiplier") + ": " + getLore(IRiftMultiplier.RiftMultiplierTypes.SPEED, itemblock.field_150939_a));
            loreList.add(translate("qa.lore.multipliers.energy", "Energy Usage") + ": " + getLore(IRiftMultiplier.RiftMultiplierTypes.ENERGY_USAGE, itemblock.field_150939_a));
            loreList.add(translate("qa.lore.multipliers.production", "Production") + ": " + getLore(IRiftMultiplier.RiftMultiplierTypes.PRODUCTION, itemblock.field_150939_a));
        } else {
            loreList.add(String.format("%s" + translate("qa.lore.multipliers.showShift.0", "Quantum Manipulation Catalyst"), EnumChatFormatting.LIGHT_PURPLE));
            loreList.add(String.format(translate("qa.lore.multipliers.showShift.1", "Hold {c:GREEN}{c:ITALIC}<SHIFT>{c:GRAY} for details"), EnumChatFormatting.GREEN.toString() + EnumChatFormatting.ITALIC.toString(), EnumChatFormatting.GRAY));
        }
    }

    /**
     * Translates with a Default Value
     */
    public static String translate(String key, String def) {
        return LocalizationUtils.translateLocalWithColours(key, def);
    }
}
