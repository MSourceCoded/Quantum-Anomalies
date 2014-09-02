package sourcecoded.quantum.sceptre.focus;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;

public class FocusHelium implements ISceptreFocus {

    @Override
    public String getFocusIdentifier() {
        return "QA|helium";
    }

    @Override
    public String getName() {
        return "qa.sceptre.focus.helium";
    }

    @Override
    public String[] getLore(ItemStack item) {
        return new String[]{"qa.sceptre.focus.helium.lore.0"};
    }

    @Override
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack) {
        return true;
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
    public void onClickBegin(EntityPlayer player, ItemStack item) {
    }

    @Override
    public void onClickEnd(EntityPlayer player, ItemStack item) {
        double distance = 35F;

        Vec3 lookVec = player.getLook(1.0F);

        double force = 2.5D;
        player.motionX += force * lookVec.xCoord;
        player.motionY += force * lookVec.yCoord;
        player.motionZ += force * lookVec.zCoord;

        player.fallDistance = 0F;
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
        return Colourizer.LIGHT_BLUE.rgb;
    }
}
