package sourcecoded.quantum.api.translation;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class LocalizationUtils {

    /**
     * Translates with a Default Value
     */
    public static String translate(String key, String def) {
        String returned = StatCollector.translateToLocal(key);
        if (returned.equals(key)) returned = def;
        return returned;
    }

    /**
     * Translates the given string to local, replacing
     * '{c:COLOUR}' with the appropriate chat colour
     */
    public static String translateLocalWithColours(String key, String def) {
        String n = translate(key, def);
        for (EnumChatFormatting color : EnumChatFormatting.values()) {
            String match = String.format("{c:%s}", color.name());
            n = n.replace(match, color.toString());
        }
        return n;
    }

}
