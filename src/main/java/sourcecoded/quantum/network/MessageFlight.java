package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MessageFlight implements IMessage, IMessageHandler<MessageFlight, IMessage> {

    boolean enabled;

    public MessageFlight() {}
    public MessageFlight(boolean allowed) {
        this.enabled = allowed;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        enabled = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(enabled);
    }

    @Override
    public IMessage onMessage(MessageFlight message, MessageContext ctx) {
        ctx.getServerHandler().playerEntity.capabilities.allowFlying = message.enabled;
        if (!message.enabled)
            ctx.getServerHandler().playerEntity.capabilities.isFlying = false;
        return null;
    }
}
