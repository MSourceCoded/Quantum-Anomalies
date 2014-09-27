package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class MessageBlockBreakFX implements IMessage, IMessageHandler<MessageBlockBreakFX, IMessage> {

    public int x, y, z;

    public MessageBlockBreakFX() {}
    public MessageBlockBreakFX(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public IMessage onMessage(MessageBlockBreakFX message, MessageContext ctx) {
        Minecraft mc = Minecraft.getMinecraft();
        x = message.x;
        y = message.y;
        z = message.z;
        mc.effectRenderer.addBlockDestroyEffects(x, y, z, mc.theWorld.getBlock(x, y, z), mc.theWorld.getBlockMetadata(x, y, z));
        return null;
    }
}
