package sourcecoded.quantum.sceptre;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.gesture.IGestureCallback;
import sourcecoded.quantum.api.gesture.template.GestureCircle;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;

public class FocusBlue implements ISceptreFocus, IGestureCallback {
    @Override
    public String getFocusIdentifier() {
        return "Blue";
    }

    @Override
    public String getName() {
        return "Blue Focus";
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.BLUE;
    }

    @Override
    public void onActivated(ItemStack item) {

    }

    @Override
    public void onDeactivated(ItemStack item) {

    }

    @Override
    public void onItemTick(ItemStack item) {

    }

    @Override
    public void onUsingTick(ItemStack item) {

    }

    @Override
    public IGesture[] getAvailableGestures() {
        return new IGesture[] {new GestureCircle(this)};
    }

    @Override
    public float[] getRGB() {
        return new float[] {0F, 0F, 0.5F};
    }

    @Override
    public void callbackGesture(EntityPlayer player, World world, GesturePointMap pointMap) {
        player.addChatComponentMessage(new ChatComponentText("You drew a circle!"));
    }
}
