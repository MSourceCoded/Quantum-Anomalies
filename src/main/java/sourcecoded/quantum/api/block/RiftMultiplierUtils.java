package sourcecoded.quantum.api.block;

import net.minecraft.block.Block;
import net.minecraft.util.EnumChatFormatting;

public class RiftMultiplierUtils {

    public static String getLore(IRiftMultiplier.RiftMultiplierTypes type, Block block) {
        float data = ((IRiftMultiplier) block).getRiftMultiplication(type);

        if (data < 1F) return type.negative + "-" + Math.round((1F - data) * 100) + "%";
        if (data > 1F) return type.positive + "+" + Math.round((data - 1F) * 100) + "%";
        return EnumChatFormatting.GOLD + "N/A";
    }

}
