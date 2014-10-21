package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import sourcecoded.quantum.api.discovery.DiscoveryManager;

public class MessageDiscoveryItem implements IMessage, IMessageHandler<MessageDiscoveryItem, IMessage> {

    String name;
    boolean force;
    int type = 0;

    public MessageDiscoveryItem() {}
    public MessageDiscoveryItem(String name) {
        this.name = name;
        this.force = false;
    }
    public MessageDiscoveryItem(String name, boolean force) {
        this.name = name;
        this.force = force;
    }

    public MessageDiscoveryItem setTypeUnlock() {
        this.type = 0;
        return this;
    }

    public MessageDiscoveryItem setTypeHidden() {
        this.type = 1;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
        force = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        buf.writeBoolean(force);
    }

    @Override
    public IMessage onMessage(MessageDiscoveryItem message, MessageContext ctx) {
        if (message.type == 0)
            DiscoveryManager.unlockItem(message.name, ctx.getServerHandler().playerEntity, message.force);
        else if (message.type == 1)
            DiscoveryManager.revealItem(message.name, ctx.getServerHandler().playerEntity);
        return null;
    }
}
