package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class MessageVanillaParticle implements IMessage, IMessageHandler<MessageVanillaParticle, IMessage> {

    String name;
    double x, y, z, velX, velY, velZ;
    int amount;

    public MessageVanillaParticle() {}
    public MessageVanillaParticle(String name, double x, double y, double z, double velX, double velY, double velZ, int amount) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.amount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        velX = buf.readDouble();
        velY = buf.readDouble();
        velZ = buf.readDouble();
        amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);
        buf.writeInt(amount);
    }

    @Override
    public IMessage onMessage(MessageVanillaParticle message, MessageContext ctx) {
        for (int i = 0; i < message.amount; i++)
            Minecraft.getMinecraft().theWorld.spawnParticle(message.name, message.x, message.y, message.z, message.velX, message.velY, message.velZ);
        return null;
    }
}
