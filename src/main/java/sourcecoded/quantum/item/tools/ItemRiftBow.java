package sourcecoded.quantum.item.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.client.renderer.item.TexDepthMap;
import sourcecoded.quantum.entity.EntityQuantumArrow;
import sourcecoded.quantum.inventory.QATabs;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public class ItemRiftBow extends ItemBow implements ICraftableItem {

    static String resourceStandby = "textures/items/tools/bow_standby_";
    static String resourceDraw = "textures/items/tools/bow_draw_";

    static TexDepthMap[] mapStandby = new TexDepthMap[] {
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceStandby + "0.png"), 0.04F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceStandby + "1.png"), 0.02F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceStandby + "2.png"), 0.07F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceStandby + "3.png"), 0.09F)
    };

    static TexDepthMap[] mapDrawStart = new TexDepthMap[] {
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_0.png"), 0.03F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_1.png"), 0.06F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_2.png"), 0.04F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_3.png"), 0.02F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_4.png"), 0.07F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "0_5.png"), 0.09F)
    };

    static TexDepthMap[] mapDrawEnd = new TexDepthMap[] {
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_0.png"), 0.03F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_1.png"), 0.06F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_2.png"), 0.04F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_3.png"), 0.02F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_4.png"), 0.07F),
        new TexDepthMap(new ResourceLocation(Constants.MODID, resourceDraw + "1_5.png"), 0.09F)
    };

    public IArrangementRecipe recipe = new ArrangementShapedRecipe(new ItemStack(this), " se", "ser", " se", 's', QAItems.INJECTED_STRING.getItem(), 'r', new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1), 'e', QAItems.ENTROPIC_STAR.getItem());

    public static int maxDraw = 5;

    public ItemRiftBow() {
        super();

        this.setUnlocalizedName("itemRiftBow");
        this.setTextureName("tools/bowFull");

        this.setMaxDamage(0);

        this.setCreativeTab(QATabs.quantumTab);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.worldObj.isRemote) {
            if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();

            int index = 0;
            int use = getMaxItemUseDuration(stack) - count;

            if (use >= maxDraw)
                index = 1;
            else if (use >= maxDraw / 2)
                index = 0;
            else if (use >= 0)
                index = -1;

            stack.stackTagCompound.setInteger("iconIndex", index);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count) {
        if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
        if (world.isRemote) {
            stack.stackTagCompound.setInteger("iconIndex", -1);
            stack.stackTagCompound.setBoolean("inUse", false);
        }

        int pullTime = this.getMaxItemUseDuration(stack) - count;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow)) {
            //float scaledPull = (float)pullTime / 10F;
            //scaledPull = (scaledPull * scaledPull + scaledPull * 2.0F) / 3.0F;

            float scaledPull = (float)pullTime / (float)maxDraw;

            if ((double)scaledPull < 0.05D)
                return;

            if (scaledPull > 1.0F)
                scaledPull = 1.0F;

            EntityQuantumArrow arrow = new EntityQuantumArrow(world, player, scaledPull * 2.9F, 2D);     //TODO change damage

            if (scaledPull == 1.0F)
                arrow.setIsCritical(true);

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
                arrow.setDamage(arrow.getDamage() + (double)k * 0.5D + 0.5D);

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
                arrow.setKnockbackStrength(l);

            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + scaledPull * 0.5F);

            if (flag)
                arrow.canBePickedUp = 2;
            else
                player.inventory.consumeInventoryItem(Items.arrow);

            if (!world.isRemote)
                world.spawnEntityInWorld(arrow);
        }
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 || player.inventory.hasItem(Items.arrow)) {
            if (world.isRemote)
                stack.stackTagCompound.setBoolean("inUse", true);
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }

    public static TexDepthMap[] getDepthMap(int index) {
        ArrayList<TexDepthMap> map = new ArrayList<TexDepthMap>();

        if (index == 0)
            return mapDrawStart;
        if (index == 1)
            return mapDrawEnd;

        return mapStandby;
    }

    //Bleh
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
    }

    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    public String customName;

    public List<String> loreList = new ArrayList<String>();

    public Item setTextureName(String name) {
        return super.setTextureName(prefix(Constants.MODID, name));
    }

    public Item setUnlocalizedName(String name) {
        customName = name;
        return super.setUnlocalizedName(prefix(Constants.MODID_SHORT, name));
    }

    public String getUnlocalizedName(ItemStack item) {
        String base = "qa.items." + customName;
        return base;
    }

    public String getItemStackDisplayName(ItemStack item) {
        String translateString = getUnlocalizedName(item) + ".name";
        if (hasSubtypes)
            translateString += "@" + item.getItemDamage();

        return LocalizationUtils.translateLocalWithColours(translateString, translateString);
    }

    public void tryLore(String unlocalizedBase, ItemStack stack) {
        loreList = new ArrayList<String>();
        boolean search = true;
        int run = 0;
        while (search) {
            String formatted = unlocalizedBase + ".lore." + run;
            if (hasSubtypes)
                formatted += "@" + stack.getItemDamage();

            if (StatCollector.canTranslate(formatted)) {
                loreList.add(LocalizationUtils.translateLocalWithColours(formatted, "You shouldn't be seeing this, report this please"));
            } else search = false;
            run++;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean idk) {
        tryLore(itemStack.getUnlocalizedName(), itemStack);
        list.addAll(loreList);
    }

    @Override
    public IRecipe[] getRecipes(Item item) {
        ArrangementRegistry.addRecipe(recipe);
        return new IRecipe[0];
    }
}
