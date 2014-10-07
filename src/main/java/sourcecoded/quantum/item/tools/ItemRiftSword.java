package sourcecoded.quantum.item.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.inventory.QATabs;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public class ItemRiftSword extends ItemSword implements ICraftableItem {

    public ItemRiftSword() {
        super(QuantumAnomalies.materialRift);

        this.setMaxStackSize(1);
        this.setUnlocalizedName("itemRiftSword");
        this.setTextureName("tools/sword");

        this.setMaxDamage(0);

        this.setCreativeTab(QATabs.quantumTab);
    }

    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if (!stack.hasTagCompound())
            stack.stackTagCompound = new NBTTagCompound();

        double dmg = entity.fallDistance / 2F;

        stack.stackTagCompound.setDouble("damageModifier", dmg);
    }

    public Multimap getAttributeModifiers(ItemStack stack) {
        if (!stack.hasTagCompound())
            stack.stackTagCompound = new NBTTagCompound();
        if (!stack.stackTagCompound.hasKey("damageModifier"))
            stack.stackTagCompound.setDouble("damageModifier", 1D);

        double dmg = stack.stackTagCompound.getDouble("damageModifier");

        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", dmg + QuantumAnomalies.materialRift.getDamageVsEntity() + 5, 0));
        return multimap;
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
        String translate = LocalizationUtils.translateLocalWithColours(translateString, translateString);

        return translate;
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
        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(this), " s ", " s ", " i ", 's', QAItems.ENTROPIC_STAR.getItem(), 'i', new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1)));
        return new IRecipe[0];
    }
}
