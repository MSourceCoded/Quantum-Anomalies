package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.stats.StatList;
import sourcecoded.quantum.api.discovery.DiscoveryManager;

public class MessageAchievement implements IMessage, IMessageHandler<MessageAchievement, IMessage> {

    String name;

    public MessageAchievement() {}
    public MessageAchievement(String name) {
        this.name = name;
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
    public IMessage onMessage(MessageAchievement message, MessageContext ctx) {
        ctx.getServerHandler().playerEntity.addStat(StatList.func_151177_a(message.name), 1);
        return null;
    }
}
