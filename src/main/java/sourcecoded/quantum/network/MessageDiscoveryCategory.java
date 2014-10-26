package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import sourcecoded.quantum.api.discovery.DiscoveryManager;

public class MessageDiscoveryCategory implements IMessage, IMessageHandler<MessageDiscoveryCategory, IMessage> {

    String name;
    int type = 0;

    public MessageDiscoveryCategory() {}
    public MessageDiscoveryCategory(String name) {
        this.name = name;
    }

    public MessageDiscoveryCategory setTypeUnlock() {
        this.type = 0;
        return this;
    }

    public MessageDiscoveryCategory setTypeHidden() {
        this.type = 1;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils .readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public IMessage onMessage(MessageDiscoveryCategory message, MessageContext ctx) {
        if (message.type == 0)
            DiscoveryManager.unlockCategory(message.name, ctx.getServerHandler().playerEntity);
        else if (message.type == 1)
            DiscoveryManager.revealCategory(message.name, ctx.getServerHandler().playerEntity);
        return null;
    }
}
