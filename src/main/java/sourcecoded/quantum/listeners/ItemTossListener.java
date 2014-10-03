package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import sourcecoded.quantum.entity.properties.PropertiesItem;

public class ItemTossListener {

    @SubscribeEvent
    public void onItemToss(ItemTossEvent event) {
        EntityItem item = event.entityItem;
        EntityPlayer player = event.player;

        PropertiesItem itemProps = new PropertiesItem();
        itemProps.tosser = player.getUniqueID().toString();

        item.registerExtendedProperties("tossData", itemProps);
    }

}
