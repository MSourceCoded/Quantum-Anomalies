package sourcecoded.quantum.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import sourcecoded.quantum.util.save.QAWorldSavedData;

import java.io.IOException;

public class MessageClientWorldData implements IMessage, IMessageHandler<MessageClientWorldData, IMessage> {

    QAWorldSavedData data;

    public MessageClientWorldData() {}
    public MessageClientWorldData(QAWorldSavedData data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = QAWorldSavedData.getInstance(FMLClientHandler.instance().getWorldClient());
        try {
            data.readFromNBT(NetworkHandler.readNBT(buf));
            data.markForUpdate(FMLClientHandler.instance().getWorldClient());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound compound = new NBTTagCompound();
        data.writeToNBT(compound);
        try {
            NetworkHandler.writeNBT(buf, compound);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(MessageClientWorldData message, MessageContext ctx) {
        return null;
    }
}
