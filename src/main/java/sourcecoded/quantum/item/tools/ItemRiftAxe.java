package sourcecoded.quantum.item.tools;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.arrangement.ArrangementRegistry;
import sourcecoded.quantum.api.arrangement.ArrangementShapedRecipe;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.inventory.QATabs;
import sourcecoded.quantum.network.MessageBlockBreakFX;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.registry.QAItems;

import java.util.ArrayList;
import java.util.List;

import static sourcecoded.core.util.LocalizationUtils.prefix;

public class ItemRiftAxe extends ItemAxe implements ICraftableItem {

    public ItemRiftAxe() {
        super(QuantumAnomalies.materialRift);

        this.setMaxStackSize(1);
        this.setUnlocalizedName("itemRiftAxe");
        this.setTextureName("tools/axe");

        this.setMaxDamage(0);

        this.setCreativeTab(QATabs.quantumTab);
    }

    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    public String customName;

    public List<String> loreList = new ArrayList<String>();

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int sideHit, float clickX, float clickY, float clickZ) {
        if (!world.isRemote) {
            search(stack, player, world, x, y, z, 0, null);
        }

        return !world.isRemote;
    }

    public void search(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int depth, Block last) {
        if (depth == 40) return;

        Block block = world.getBlock(x, y, z);
        if ((block == last || last == null) && block.getMaterial() == Material.wood) {
            int metadata = world.getBlockMetadata(x, y, z);

            NetworkHandler.wrapper.sendToAllAround(new MessageBlockBreakFX(x, y, z), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 20));
            world.setBlockToAir(x, y, z);

            boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
            int fortune = EnchantmentHelper.getFortuneModifier(player);

            if (block.canSilkHarvest(world, player, x, y, z, metadata) && silk) {
                world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(block, 1, metadata)));
            } else {
                ArrayList<ItemStack> dropList = block.getDrops(world, x, y, z, metadata, fortune);
                if (dropList != null)
                    for (ItemStack drop : dropList)
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
            }

            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                search(stack, player, world, x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, ++depth, block);
            }
        }
    }

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
        ArrangementRegistry.addRecipe(new ArrangementShapedRecipe(new ItemStack(this), " ss", " is", " i ", 's', QAItems.ENTROPIC_STAR.getItem(), 'i', new ItemStack(QAItems.INJECTED_STICK.getItem(), 1, 1)));
        return new IRecipe[0];
    }
}
