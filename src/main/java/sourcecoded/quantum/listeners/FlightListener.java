package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import sourcecoded.quantum.network.MessageFlight;
import sourcecoded.quantum.network.NetworkHandler;

public class FlightListener {

    public static FlightListener instance;
    public static FlightListener getInstance() {
        if (instance == null)
            instance = new FlightListener();
        return instance;
    }

    public static int currentTicker;
    public static int maximumTicker = 10;

    public static void resetCounter() {
        if (currentTicker < 1 || !Minecraft.getMinecraft().thePlayer.capabilities.allowFlying) {
            NetworkHandler.wrapper.sendToServer(new MessageFlight(true));
            Minecraft.getMinecraft().thePlayer.capabilities.allowFlying = true;
        }

        currentTicker = maximumTicker;
    }

    @SubscribeEvent
    public void clientTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.CLIENT && currentTicker > 0) {
            currentTicker--;
            if (currentTicker == 1) {
                NetworkHandler.wrapper.sendToServer(new MessageFlight(false));
                Minecraft.getMinecraft().thePlayer.capabilities.allowFlying = false;
                Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
            }
        }
    }

}
