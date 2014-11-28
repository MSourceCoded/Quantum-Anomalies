package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraftforge.client.event.GuiScreenEvent;
import sourcecoded.quantum.discovery.QADiscoveries;
import sourcecoded.quantum.network.MessageDiscoveryItem;
import sourcecoded.quantum.network.NetworkHandler;

public class DiscoveryListenerClient {
    @SubscribeEvent
    public void guiButton(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiScreenBook && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (event.button.id == 3)
                NetworkHandler.wrapper.sendToServer(new MessageDiscoveryItem(QADiscoveries.Item.SHELF.get().getKey(), false));
        }
    }

    public void gui(GuiScreenEvent.InitGuiEvent.Pre event) {
        if (event.gui instanceof GuiEnchantment && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            NetworkHandler.wrapper.sendToServer(new MessageDiscoveryItem(QADiscoveries.Item.ENCHANT.get().getKey(), false));
        }
    }
}
