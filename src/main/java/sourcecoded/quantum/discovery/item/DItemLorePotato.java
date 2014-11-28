package sourcecoded.quantum.discovery.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryPage;
import sourcecoded.quantum.discovery.QADiscoveries;

public class DItemLorePotato extends DiscoveryItem {

    public DItemLorePotato() {
        super("QA|Potato");

        this.displayStack = new ItemStack(Items.potato);

        this.x = 100; this.y = 90;

        this.setHiddenByDefault(true);
        this.setUnlockedByDefault(false);

        this.addParent(QADiscoveries.Item.RF.get().getKey());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 75; i++)
            builder.append("potato ");

        for (int p = 0; p < 10; p++)
            this.addPage(new DiscoveryPage(this.getUnlocalizedName(), builder.toString()));
    }

    @Override
    public int overrideUnlock(EntityPlayer player) {
        int hotbar = InventoryPlayer.getHotbarSize();

        int returnVal = OVERRIDE;

        boolean potato = potato(player, hotbar);

        if (potato)
            returnVal = returnVal | UNLOCKED_OR_HIDDEN;

        return returnVal;
    }

    @Override
    public int overrideHidden(EntityPlayer player) {
        int hotbar = InventoryPlayer.getHotbarSize();

        int returnVal = OVERRIDE;

        boolean potato = potato(player, hotbar);

        if (!potato)
            returnVal = returnVal | UNLOCKED_OR_HIDDEN;

        return returnVal;
    }

    private boolean potato(EntityPlayer player, int hotbar) {
        boolean potato = false;

        for (int i = 0; i < hotbar; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == Items.potato)
                potato = true;
        }
        return potato;
    }
}