package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import sun.plugin2.message.Message;

public class NetworkHandler {

    public static SimpleNetworkWrapper wrapper;

    public static void initNetwork() {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("sc|quantumAnomalies");

        wrapper.registerMessage(MessageAchievement.class, MessageAchievement.class, 0, Side.SERVER);
    }

}
