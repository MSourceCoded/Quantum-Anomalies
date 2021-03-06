package sourcecoded.quantum.item.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.inventory.QATabs;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public class ItemRiftPickaxe extends ItemPickaxe implements ICraftableItem {

    public ItemRiftPickaxe() {
        super(QuantumAnomalies.materialRift);

        this.setMaxStackSize(1);
        this.setUnlocalizedName("itemRiftPickaxe");
        this.setTextureName("tools/pickaxe");

        this.setMaxDamage(0);

        this.setCreativeTab(QATabs.quantumTab);
    }

    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float clickX, float clickY, float clickZ) {
        ItemStack blockStack = new ItemStack(QABlocks.ENERGISED_AIR.getBlock());

        blockStack.getItem().onItemUse(blockStack, player, world, x, y, z, sideHit, clickX, clickY, clickZ);

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
        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(this), "sss", " i ", " i ", 's', QAItems.ENTROPIC_STAR.getItem(), 'i', new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1)));
        return new IRecipe[0];
    }
}
