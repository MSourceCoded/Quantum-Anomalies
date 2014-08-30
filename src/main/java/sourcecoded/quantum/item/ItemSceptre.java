package sourcecoded.quantum.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sourcecoded.quantum.api.gesture.GesturePointMap;
import sourcecoded.quantum.api.gesture.IGesture;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.api.sceptre.SceptreFocusRegistry;
import sourcecoded.quantum.utils.ItemUtils;

public class ItemSceptre extends ItemQuantum {

    public GesturePointMap mostRecentGesture = new GesturePointMap();

    public float[] offsetInitial = new float[2];

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
                String focusName = SceptreFocusRegistry.getFocus(item.stackTagCompound.getString("focus")).getName();
                EnumChatFormatting focusColour = SceptreFocusRegistry.getFocus(item.stackTagCompound.getString("focus")).getNameColour();
                return String.format("%s %s[%s]%s", baseName, focusColour, focusName, EnumChatFormatting.WHITE);
            } else return String.format("%s %s[%s]%s", baseName, EnumChatFormatting.LIGHT_PURPLE, nullFocus, EnumChatFormatting.WHITE);
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
            ISceptreFocus focus = SceptreFocusRegistry.getFocus(item.stackTagCompound.getString("focus"));

            if (focus != null) {
                focus.onItemTick(item);
                rgb = focus.getRGB();
            } else item.stackTagCompound.removeTag("focus");
        }

        if (rgb == null) rgb = new float[] {0.8F, 0F, 0.8F};

        float oR = colours.getFloat("r");
        float oG = colours.getFloat("g");
        float oB = colours.getFloat("b");

        if (oR > rgb[0]+step) oR -= step; else if (oR < rgb[0]-step) oR += step;
        if (oG > rgb[1]+step) oG -= step; else if (oG < rgb[1]-step) oG += step;
        if (oB > rgb[2]+step) oB -= step; else if (oB < rgb[2]-step) oB += step;

        colours.setFloat("r", oR);
        colours.setFloat("g", oG);
        colours.setFloat("b", oB);

        item.stackTagCompound.setTag("colourData", colours);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
        if (world.isRemote) {
            ISceptreFocus focus = SceptreFocusRegistry.getFocus(stack.stackTagCompound.getString("focus"));
            if (focus != null) {
                IGesture[] gestures = focus.getAvailableGestures();
                if (gestures != null) {
                    for (IGesture gesture : gestures)
                       if (gesture.calculateGesture(player, world, stack, mostRecentGesture)) return;
                }
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) {
            mostRecentGesture = new GesturePointMap();

            Minecraft mc = Minecraft.getMinecraft();        //Wrap me to the beginning
            offsetInitial = new float[]{mc.thePlayer.getRotationYawHead(), mc.thePlayer.rotationPitch};
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
            focus = SceptreFocusRegistry.getNextFocus(SceptreFocusRegistry.getFocus(stack.stackTagCompound.getString("focus")), (EntityPlayer)entityLiving, stack);
        else
            focus = SceptreFocusRegistry.getNextFocus(null, (EntityPlayer)entityLiving, stack);

        changeFocus(focus, stack);
        return false;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.none;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @SideOnly(Side.CLIENT)
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        Minecraft mc = Minecraft.getMinecraft();        //Wrap me to the beginning

        float yaw = offsetInitial[0] - mc.thePlayer.getRotationYawHead();
        float pitch = offsetInitial[1] - mc.thePlayer.rotationPitch;
        mostRecentGesture.addPoint(yaw, pitch);
    }

    void changeFocus(ISceptreFocus focus, ItemStack stack) {
        ISceptreFocus oldFocus = SceptreFocusRegistry.getFocus(stack.stackTagCompound.getString("focus"));
        if (oldFocus != null) oldFocus.onDeactivated(stack);

        if (focus != null) {
            stack.stackTagCompound.setString("focus", focus.getFocusIdentifier());
            focus.onActivated(stack);
        } else stack.stackTagCompound.removeTag("focus");
    }
}
