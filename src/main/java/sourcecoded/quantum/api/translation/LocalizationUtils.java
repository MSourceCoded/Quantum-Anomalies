package sourcecoded.quantum.api.translation;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * A static utility class for Localization. This is
 * used for hooking into the standard formatting of the
 * .lang files of Quantum Anomalies. This is not required
 * as part of the API, but I decided to include it for others
 * to use if they feel inclined to.
 *
 * @author SourceCoded
 */
public class LocalizationUtils {

    /**
     * Translates a given key to the local language with
     * a default fallback value.
     *
     * @see net.minecraft.util.StatCollector#translateToLocal(String)
     */
    public static String translate(String key, String def) {
        String returned = StatCollector.translateToLocal(key);
        if (returned.equals(key)) returned = def;
        return returned;
    }

    /**
     * Translates the given string to local, replacing
     * '{c:COLOUR}' with the appropriate chat colour
     *
     * @see #translate(String, String)
     * @see net.minecraft.util.StatCollector#translateToLocal(String)
     */
    public static String translateLocalWithColours(String key, String def) {
        String n = translate(key, def);
        for (EnumChatFormatting color : EnumChatFormatting.values()) {
            String match = String.format("{c:%s}", color.name());
            n = n.replace(match, color.toString());
        }
        return n;
    }

    /**
     * Escapes all EnumChatFormatting from a text
     */
    public static String escapeFormatting(String text) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(text);
    }

    /**
     * Patches a list through translateLocalWithColours
     */
    @SuppressWarnings("unchecked")
    public static List<String> translateList(List<String> string) {
        List<String> list = new ArrayList<String>();
        for (String s : string) {
            list.addAll(Arrays.asList(translateLocalWithColours(s, s).split("<br>")));
        }
        return list;
    }

}
