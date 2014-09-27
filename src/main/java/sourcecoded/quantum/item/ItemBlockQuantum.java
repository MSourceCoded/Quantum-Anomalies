package sourcecoded.quantum.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemBlockQuantum extends ItemBlock {

    public List<String> loreList;
    public boolean gotLore = false;

    public ItemBlockQuantum(Block block) {
        super(block);
        loreList = new ArrayList<String>();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return this.field_150939_a.getLocalizedName();
    }

    public void tryLore(String unlocalizedBase) {
        boolean search = true;
        gotLore = true;
        int run = 0;
        while (search) {
            String formatted = unlocalizedBase + ".lore." + run;
            if (StatCollector.canTranslate(formatted)) {
                loreList.add(LocalizationUtils.translateLocalWithColours(formatted, "You shouldn't be seeing this, report this please"));
            } else search = false;
            run++;
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean idk) {
        if (!gotLore)
            tryLore(itemStack.getUnlocalizedName());
        list.addAll(loreList);
    }
}
