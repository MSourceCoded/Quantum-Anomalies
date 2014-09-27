package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {

    public static SimpleNetworkWrapper wrapper;

    public static void initNetwork() {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("sc|quantumAnomalies");

        /* Sending to server */
        wrapper.registerMessage(MessageAchievement.class, MessageAchievement.class, 0, Side.SERVER);

        /* Sending to client */
        wrapper.registerMessage(MessageVanillaParticle.class, MessageVanillaParticle.class, 0, Side.CLIENT);
        wrapper.registerMessage(MessageBlockBreakFX.class, MessageBlockBreakFX.class, 1, Side.CLIENT);
    }

}
