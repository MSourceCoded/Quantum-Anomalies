package sourcecoded.quantum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.crafting.vacuum.VacuumPencil;
import sourcecoded.quantum.util.save.QAWorldSavedData;

public class ItemPencil extends ItemQuantum implements ICraftableItem {

    public ItemPencil() {
        this.setTextureName("pencil");
        this.setUnlocalizedName("itemRiftPencil");
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            String itemName = stack.getDisplayName();

            if (player.isSneaking())
                QAWorldSavedData.getInstance(world).destroyBlockLabel(world, x, y, z, true);
            else if (!itemName.equals(this.getItemStackDisplayName(stack)))
                QAWorldSavedData.getInstance(world).injectBlockLabel(world, x, y, z, itemName);

            return true;
        }
        return false;
    }

    @Override
    public IRecipe[] getRecipes(Item item) {
        VacuumRegistry.addRecipe(new VacuumPencil());
        return new IRecipe[0];
    }
}