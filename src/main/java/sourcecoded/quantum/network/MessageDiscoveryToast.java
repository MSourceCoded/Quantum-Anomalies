package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import sourcecoded.quantum.client.gui.GuiHandler;

public class MessageDiscoveryToast implements IMessage, IMessageHandler<MessageDiscoveryToast, IMessage> {

    String name;

    public MessageDiscoveryToast() {}
    public MessageDiscoveryToast(String name) {
        this.name = name;
    }
    public MessageDiscoveryToast(String name, boolean force) {
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public IMessage onMessage(MessageDiscoveryToast message, MessageContext ctx) {
        GuiHandler.unlocked.setItem(message.name);
        return null;
    }
}
