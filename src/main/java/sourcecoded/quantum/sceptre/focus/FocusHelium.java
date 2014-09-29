package sourcecoded.quantum.sceptre.focus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.gesture.AbstractGesture;
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
    public void onClickBegin(EntityPlayer player, ItemStack item, World world) {
    }

    @Override
    public void onClickEnd(EntityPlayer player, ItemStack item, World world, int ticker) {
        double distance = 35F;

        Vec3 lookVec = player.getLook(1.0F);

        double tick = Math.min(10D, (item.getItem().getMaxItemUseDuration(item) - ticker) / 10D);

        double force = 2.5D * tick;
        player.motionX += force * lookVec.xCoord;
        player.motionY += force * lookVec.yCoord;
        player.motionZ += force * lookVec.zCoord;

        player.fallDistance = 0F;
    }

    @Override
    public boolean onBlockClick(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void onItemTick(ItemStack item) {
    }

    @Override
    public void onUsingTick(ItemStack item) {
    }

    @Override
    public AbstractGesture[] getAvailableGestures() {
        return null;
    }

    @Override
    public float[] getRGB() {
        return Colourizer.LIGHT_BLUE.rgb;
    }
}
