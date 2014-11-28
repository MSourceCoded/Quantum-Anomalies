package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public class MessageDiscoveryImport implements IMessage, IMessageHandler<MessageDiscoveryImport, IMessage> {

    NBTTagCompound compound;

    public MessageDiscoveryImport() {}
    public MessageDiscoveryImport(NBTTagCompound compound) {
        this.compound = compound;
    }
    public MessageDiscoveryImport(EntityPlayer player) {
        this.compound = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag("QuantumAnomalies");
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = new NBTTagCompound();
        try {
            compound = NetworkHandler.readNBT(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            NetworkHandler.writeNBT(buf, compound);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(MessageDiscoveryImport message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

        player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag("QuantumAnomalies", message.compound);

        return null;
    }
}
