package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public class NetworkHandler {

    public static SimpleNetworkWrapper wrapper;

    public static void initNetwork() {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("sc|quantumAnomalies");

        /* Sending to server */
        wrapper.registerMessage(MessageAchievement.class, MessageAchievement.class, 0, Side.SERVER);
        wrapper.registerMessage(MessageFlight.class, MessageFlight.class, 1, Side.SERVER);
        wrapper.registerMessage(MessageChangeFocus.class, MessageChangeFocus.class, 2, Side.SERVER);
        wrapper.registerMessage(MessageDiscoveryRequest.class, MessageDiscoveryRequest.class, 3, Side.SERVER);
        wrapper.registerMessage(MessageDiscoveryItem.class, MessageDiscoveryItem.class, 4, Side.SERVER);
        wrapper.registerMessage(MessageDiscoveryCategory.class, MessageDiscoveryCategory.class, 5, Side.SERVER);

        /* Sending to client */
        wrapper.registerMessage(MessageVanillaParticle.class, MessageVanillaParticle.class, 10, Side.CLIENT);
        wrapper.registerMessage(MessageBlockBreakFX.class, MessageBlockBreakFX.class, 11, Side.CLIENT);
        wrapper.registerMessage(MessageSetPlayerVelocity.class, MessageSetPlayerVelocity.class, 12, Side.CLIENT);
        wrapper.registerMessage(MessageClientWorldData.class, MessageClientWorldData.class, 13, Side.CLIENT);
        wrapper.registerMessage(MessageDiscoveryUpdate.class, MessageDiscoveryUpdate.class, 14, Side.CLIENT);
        wrapper.registerMessage(MessageDiscoveryToast.class, MessageDiscoveryToast.class, 15, Side.CLIENT);
    }

    public static void writeNBT(ByteBuf target, NBTTagCompound tag) throws IOException {
        if (tag == null)
            target.writeShort(-1);
        else{
            byte[] abyte = CompressedStreamTools.compress(tag);
            target.writeShort((short)abyte.length);
            target.writeBytes(abyte);
        }
    }

    public static NBTTagCompound readNBT(ByteBuf dat) throws IOException {
        short short1 = dat.readShort();

        if (short1 < 0)
            return null;
        else {
            byte[] abyte = new byte[short1];
            dat.readBytes(abyte);
            //return CompressedStreamTools.decompress(abyte);
            return CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a);
        }
    }

}
