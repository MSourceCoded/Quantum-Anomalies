package sourcecoded.quantum.sceptre.focus;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import sourcecoded.core.util.RandomUtils;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.gesture.IGestureCallback;
import sourcecoded.quantum.api.gesture.template.GestureCircle;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.api.sceptre.SceptreFocusUtils;

public class FocusDematerialization implements ISceptreFocus, IGestureCallback {

    @Override
    public String getFocusIdentifier() {
        return "QA|dematerialization";
    }

    @Override
    public String getName() {
        return "qa.sceptre.focus.dematerialization";
    }

    @Override
    public String[] getLore(ItemStack item) {
        NBTTagCompound compound = SceptreFocusUtils.getAllocatedNBT(this, item);
        if (compound.hasKey("boundX"))
            return new String[]{"qa.sceptre.focus.dematerialization.lore.0", "qa.sceptre.focus.dematerialization.lore.1",
                    "Target: " + (int)compound.getDouble("boundX") + ", " + (int)compound.getDouble("boundY") + ", " + (int)compound.getDouble("boundZ")};
        return new String[]{"qa.sceptre.focus.dematerialization.lore.0", "qa.sceptre.focus.dematerialization.lore.1"};
    }

    @Override
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack) {
        return true;
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.DARK_GRAY;
    }

    @Override
    public void onActivated(ItemStack item) {
    }

    @Override
    public void onDeactivated(ItemStack item) {
    }

    @Override
    public void onClickBegin(EntityPlayer player, ItemStack item) {
        if (!player.worldObj.isRemote && player.isSneaking()) {
            NBTTagCompound compound = SceptreFocusUtils.getAllocatedNBT(this, item);
            compound.setDouble("boundX", player.posX);
            compound.setDouble("boundY", player.posY);
            compound.setDouble("boundZ", player.posZ);
        }
    }

    @Override
    public void onClickEnd(EntityPlayer player, ItemStack item, int ticker) {
    }

    @Override
    public void onItemTick(ItemStack item) {
    }

    @Override
    public void onUsingTick(ItemStack item) {
    }

    @Override
    public IGesture[] getAvailableGestures() {
        return new IGesture[]{new GestureCircle(this)};
    }

    @Override
    public float[] getRGB() {
        return Colourizer.GRAY.rgb;
    }

    @Override
    public void callbackGesture(EntityPlayer player, World world, GesturePointMap pointMap, ItemStack item) {
        if (!player.worldObj.isRemote) {
            NBTTagCompound compound = SceptreFocusUtils.getAllocatedNBT(this, item);
            player.setPositionAndUpdate(compound.getDouble("boundX"), compound.getDouble("boundY"), compound.getDouble("boundZ"));

            player.fallDistance = 0F;

            if (RandomUtils.nextInt(0, 3) == 2)
                player.worldObj.addWeatherEffect(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
        }
    }
}
