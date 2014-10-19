package sourcecoded.quantum.discovery;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryRegistry;
import sourcecoded.quantum.api.event.discovery.DiscoveryRegistrationEvent;
import sourcecoded.quantum.api.event.discovery.DiscoveryUpdateEvent;
import sourcecoded.quantum.discovery.category.CategoryBasics;
import sourcecoded.quantum.network.MessageDiscoveryRequest;
import sourcecoded.quantum.network.MessageDiscoveryUpdate;
import sourcecoded.quantum.network.NetworkHandler;

public class DiscoveryHandler {

    public static void init() {
        for (QADiscoveries.Category cat : QADiscoveries.Category.values())
            DiscoveryRegistry.registerCategory(cat.get());

        for (QADiscoveries.Item item : QADiscoveries.Item.values())
            item.registerItem();

        QuantumAPI.eventBus.register(new DiscoveryHandler());
        QuantumAPI.eventBus.post(new DiscoveryRegistrationEvent());
    }

    @SubscribeEvent
    public void onUpdateRequired(DiscoveryUpdateEvent event) {
        if (event.player == null) return;
        if (event.player.worldObj.isRemote) {
            //Request Update
            NetworkHandler.wrapper.sendToServer(new MessageDiscoveryRequest());
        } else {
            //Send Update
            NetworkHandler.wrapper.sendTo(new MessageDiscoveryUpdate(event.player), (EntityPlayerMP) event.player);
        }
    }

}
