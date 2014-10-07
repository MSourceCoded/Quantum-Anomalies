package sourcecoded.quantum.sceptre.focus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.api.sceptre.SceptreFocusUtils;
import sourcecoded.quantum.api.tileentity.IBindable;
import sourcecoded.quantum.api.translation.LocalizationUtils;

public class FocusBind implements ISceptreFocus {

    @Override
    public String getFocusIdentifier() {
        return "QA|Bind";
    }

    @Override
    public String getName() {
        return "qa.sceptre.focus.bind";
    }

    @Override
    public String[] getLore(ItemStack item) {
        return new String[]{"qa.sceptre.focus.bind.lore.0"};
    }

    @Override
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack) {
        return true;
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.LIGHT_PURPLE;
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
    }

    @Override
    public boolean onBlockClick(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            NBTTagCompound compound = SceptreFocusUtils.getAllocatedNBT(this, stack);

            if (player.isSneaking()) {
                compound.setInteger("bindX", x);
                compound.setInteger("bindY", y);
                compound.setInteger("bindZ", z);

                player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.bind.bindingSet", "{c:GOLD}Binding Set!")));
            } else {
                if (compound.hasKey("bindX")) {
                    TileEntity tile = world.getTileEntity(x, y, z);
                    if (tile != null && tile instanceof IBindable) {
                        IBindable bind = (IBindable) tile;
                        boolean bound = ((IBindable) tile).tryBind(player, compound.getInteger("bindX"), compound.getInteger("bindY"), compound.getInteger("bindZ"), false);
                        if (bound)
                            player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.bind.bindingComplete", "{c:AQUA}Binding Successful!")));
                    } else
                        player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.bind.notBindable", "{c:RED}You can't bind to this block. Sneak click to set binding location!")));
                } else
                    player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.bind.notSet", "{c:RED}Binding not set! Sneak click to set binding location!")));
            }
        }
        return false;
    }

    @Override
    public void onItemTick(ItemStack item) {
    }

    @Override
    public void onUsingTick(ItemStack item) {
    }

//    @Override
//    public AbstractGesture[] getAvailableGestures() {
//        return null;
//    }

    @Override
    public float[] getRGB() {
        return Colourizer.PINK.rgb;
    }
}
