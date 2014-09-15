package sourcecoded.quantum.api.sceptre;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import sourcecoded.quantum.api.gesture.AbstractGesture;

/**
 * An Interface for Sceptre Foci. This interface will allow your
 * item to be used as a focus in a sceptre. This contains all the
 * tools you need for your Scepter Focus to work correctly.
 *
 * It is recommended you use this in conjunction with Gestures,
 * although it is not a requirement
 *
 * @see sourcecoded.quantum.api.gesture.AbstractGesture
 */
public interface ISceptreFocus {

    /**
     * Called on Focus construction. This is used as
     * an identifier for the focus, stored in String
     * form. This is used in NBT data and others of the
     * like.
     *
     * It is recommended that you put your modid somewhere
     * in the name, so that there are no compatibility
     * issues with the focus.
     *
     * Names starting with 'QA|' or 'SC|' are reserved
     */
    public String getFocusIdentifier();

    /**
     * Get the actual name of the focus. This is used to
     * be displayed displayed on the tooltip. You can
     * use unlocalized names for this, like
     * modid.sceptre.focus.focusName.
     */
    public String getName();

    /**
     * Gets the 'lore' to display. This can be
     * unlocalized, such as modid.sceptre.focus.focusLore
     *
     * @param item The itemstack to add the lore to
     */
    public String[] getLore(ItemStack item);

    /**
     * Checks if the focus is currently "unlocked".
     * You can pass this through to research, or anything
     * you like.
     */
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack);

    /**
     * Get the colour for the name that will be displayed
     * in the tooltip.
     */
    public EnumChatFormatting getNameColour();

    /**
     * Called when the focus is activated.
     * @param item The ItemStack the focus is enabled on
     */
    public void onActivated(ItemStack item);

    /**
     * Called when the focus is deactivated
     * @param item The ItemStack the focus was enabled on
     */
    public void onDeactivated(ItemStack item);

    /**
     * Called when the player starts using the item
     * (when the gesture starts)
     * @param player The player clicking
     * @param item The ItemStack the focus is on
     */
    public void onClickBegin(EntityPlayer player, ItemStack item);

    /**
     * Called when the player stops using the item
     * (when the gesture ends)
     * @param player The player clicking
     * @param item The ItemStack the focus is on
     * @param ticker The amount of time the item was
     *               'drawn' for
     */
    public void onClickEnd(EntityPlayer player, ItemStack item, int ticker);

    /**
     * Called when the item is ticking (in hotbar/hand etc)
     * @param item The ItemStack the focus is ticking on
     */
    public void onItemTick(ItemStack item);

    /**
     * Called each tick the item is being used (player holding right click).
     * Usually there is no need to put something in this method if the
     * focus is using gestures, as this is handled in the IGesture
     * class
     *
     * @param item The ItemStack the focus is being used on
     */
    public void onUsingTick(ItemStack item);

    /**
     * Get an array of all available Gestures to be used with this focus.
     * This is not required, but is recommended for most foci.
     *
     * Return null if you do not wish to use Gestures.
     *
     * @see sourcecoded.quantum.api.gesture.AbstractGesture
     */
    public AbstractGesture[] getAvailableGestures();

    /**
     * Return a float array that contains an RBG color code for the focus.
     * This color code will be handled by the Sceptre automatically.
     *
     * @return A float array of length 3 containing RGB values (in their
     * respective order). Return null if you do not wish to change the
     * color of the sceptre
     */
    public float[] getRGB();
}
