package sourcecoded.quantum.sceptre;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;

public class FocusRed implements ISceptreFocus {
    @Override
    public String getFocusIdentifier() {
        return "Red";
    }

    @Override
    public String getName() {
        return "Red Focus";
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.RED;
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
        return new float[] {0.5F, 0F, 0F};
    }
}
