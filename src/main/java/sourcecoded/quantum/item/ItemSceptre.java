package sourcecoded.quantum.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.utils.ItemUtils;

import java.util.List;

public class ItemSceptre extends ItemQuantum {

    public GesturePointMap mostRecentGestureServer = new GesturePointMap();
    public GesturePointMap mostRecentGestureClient = new GesturePointMap();

    public float[] offsetInitialServer = new float[2];
    public float[] offsetInitialClient = new float[2];

    public float step = 0.025F;

    public ItemSceptre() {
        this.setUnlocalizedName("itemSceptre");
    }

    public String getItemStackDisplayName(ItemStack item) {
        //String baseName = EnumChatFormatting.GOLD + "Sceptre";
        String baseName = EnumChatFormatting.GOLD + StatCollector.translateToLocal("qa.sceptre.name");
        String nullFocus = StatCollector.translateToLocal("qa.sceptre.focus.null");

        if (item.stackTagCompound != null)
            if (item.stackTagCompound.hasKey("focus")) {
                String focusName = getFocus(item).getName();
                EnumChatFormatting focusColour = SceptreFocusRegistry.getFocus(item.stackTagCompound.getString("focus")).getNameColour();
                return String.format("%s %s[%s]%s", baseName, focusColour, StatCollector.translateToLocal(focusName), EnumChatFormatting.WHITE);
            } else
                return String.format("%s %s[%s]%s", baseName, EnumChatFormatting.LIGHT_PURPLE, nullFocus, EnumChatFormatting.WHITE);
        else return baseName;
    }

    public void onUpdate(ItemStack item, World world, Entity heldEnt, int meta, boolean held) {
        ItemUtils.checkCompound(item);

        if (!item.stackTagCompound.hasKey("colourData")) {
            NBTTagCompound colours = new NBTTagCompound();
            colours.setFloat("r", 0.8F);
            colours.setFloat("g", 0.0F);
            colours.setFloat("b", 0.8F);
        }

        float rgb[] = null;

        NBTTagCompound colours = item.stackTagCompound.getCompoundTag("colourData");

        if (item.stackTagCompound.hasKey("focus")) {
            ISceptreFocus focus = getFocus(item);

            if (focus != null) {
                focus.onItemTick(item);
                rgb = focus.getRGB();
            } else item.stackTagCompound.removeTag("focus");
        }

        if (rgb == null) rgb = new float[]{0.8F, 0F, 0.8F};

        float oR = colours.getFloat("r");
        float oG = colours.getFloat("g");
        float oB = colours.getFloat("b");

        if (oR > rgb[0] + step) oR -= step;
        else if (oR < rgb[0] - step) oR += step;
        if (oG > rgb[1] + step) oG -= step;
        else if (oG < rgb[1] - step) oG += step;
        if (oB > rgb[2] + step) oB -= step;
        else if (oB < rgb[2] - step) oB += step;

        colours.setFloat("r", oR);
        colours.setFloat("g", oG);
        colours.setFloat("b", oB);

        item.stackTagCompound.setTag("colourData", colours);
    }

    GesturePointMap getMostRecentGesture(World world) {
        if (world.isRemote) return mostRecentGestureClient; else return mostRecentGestureServer;
    }

    float[] getOffset(World world) {
        if (world.isRemote) return offsetInitialClient; else return offsetInitialServer;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
        ISceptreFocus focus = getFocus(stack);
        if (focus != null) {
            focus.onClickEnd(player, stack, count);
            IGesture[] gestures = focus.getAvailableGestures();
            if (gestures != null) {
                for (IGesture gesture : gestures)
                    if (gesture.calculateGesture(player, world, stack, getMostRecentGesture(world))) return;
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        ISceptreFocus focus = getFocus(stack);
        if (focus != null)
            focus.onClickBegin(player, stack);

        if (!world.isRemote) {
            mostRecentGestureServer = new GesturePointMap();

            offsetInitialServer = new float[]{player.getRotationYawHead(), player.rotationPitch};
        } else {
            mostRecentGestureClient = new GesturePointMap();

            offsetInitialClient = new float[]{player.getRotationYawHead(), player.rotationPitch};
        }

        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        ISceptreFocus focus;

        if (entityLiving.isSneaking()) {
            changeFocus(null, stack);
            return false;
        }

        if (stack.stackTagCompound.hasKey("focus"))
            focus = SceptreFocusRegistry.getNextFocus(getFocus(stack), (EntityPlayer) entityLiving, stack);
        else
            focus = SceptreFocusRegistry.getNextFocus(null, (EntityPlayer) entityLiving, stack);

        changeFocus(focus, stack);
        return false;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.none;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        float yaw = getOffset(player.worldObj)[0] - player.getRotationYawHead();
        float pitch = getOffset(player.worldObj)[1] - player.rotationPitch;
        getMostRecentGesture(player.worldObj).addPoint(yaw, pitch);
    }

    void changeFocus(ISceptreFocus focus, ItemStack stack) {
        ISceptreFocus oldFocus = getFocus(stack);
        if (oldFocus != null) oldFocus.onDeactivated(stack);

        if (focus != null) {
            stack.stackTagCompound.setString("focus", focus.getFocusIdentifier());
            focus.onActivated(stack);
        } else stack.stackTagCompound.removeTag("focus");
    }

    ISceptreFocus getFocus(ItemStack stack) {
        return SceptreFocusRegistry.getFocus(stack.stackTagCompound.getString("focus"));
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List lore, boolean idk) {
        ItemUtils.checkCompound(stack);
        if (getFocus(stack) != null) {
            String[] array = getFocus(stack).getLore(stack);
            for (String s : array)
                lore.add(StatCollector.translateToLocal(s));
        }
    }
}
