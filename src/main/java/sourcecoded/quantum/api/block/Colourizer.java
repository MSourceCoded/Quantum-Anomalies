package sourcecoded.quantum.api.block;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * An Enumeration containing all the available
 * colours to be used in Dyeables and other
 * various implementations
 *
 * @see sourcecoded.quantum.api.tileentity.IDyeable
 *
 * @author SourceCoded
 */
public enum Colourizer {

    BLACK("Black", 0F, 0F, 0F),
    RED("Red", 1F, 0F, 0F),
    GREEN("Green", 0F, 0.6F, 0F),
    BROWN("Brown", 0.4F, 0.3F, 0.1F),
    BLUE("Blue", 0F, 0F, 1F),
    PURPLE("Purple", 0.7F, 0F, 0.7F),
    CYAN("Cyan", 0.4F, 0.6F, 0.8F),
    LIGHT_GRAY("LightGray", 0.7F, 0.7F, 0.7F),
    GRAY("Gray", 0.5F, 0.5F, 0.5F),
    PINK("Pink", 0.9F, 0.5F, 0.9F),
    LIME("Lime", 0F, 1F, 0F),
    YELLOW("Yellow", 1F, 1F, 0F),
    LIGHT_BLUE("LightBlue", 0F, 0.5F, 1F),
    MAGENTA("Magenta", 0.7F, 0.5F, 0.7F),
    ORANGE("Orange", 1F, 0.5F, 0F),
    WHITE("White", 1F, 1F, 1F),
    RAINBOW("rainbow", 0F, 0F, 0F);

    public float[] rgb;
    public String oredictName;

    Colourizer(String name, float r, float g, float b) {
        rgb = new float[] {r, g, b};
        this.oredictName = "dye"+name;
    }

    public int toInteger() {
        int base = 0;
        base += rgb[0] * 255;
        base = base << 8;
        base += rgb[1] * 255;
        base = base << 8;
        base += rgb[2] * 255;

        return base;
    }

    public static Colourizer match(ItemStack stack) {
        if (stack.getItem() == Items.nether_star) return RAINBOW;

        int[] names = OreDictionary.getOreIDs(stack);
        for (int n : names) {
            String oredict = OreDictionary.getOreName(n);
            for (Colourizer colour : Colourizer.values()) {
                if (colour.oredictName.equals(oredict)) return colour;
            }
        }
        return null;
    }

}
