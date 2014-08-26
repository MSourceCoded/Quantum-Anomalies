package sourcecoded.quantum.sceptre;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;

public class FocusGreen implements ISceptreFocus {
    @Override
    public String getFocusIdentifier() {
        return "Green";
    }

    @Override
    public String getName() {
        return "Green Focus";
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.GREEN;
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
        return null;
    }

    @Override
    public float[] getRGB() {
        return new float[] {0F, 0.5F, 0F};
    }
}
