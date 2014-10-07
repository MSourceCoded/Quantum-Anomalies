package sourcecoded.quantum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sourcecoded.quantum.item.ItemSceptre;
import sourcecoded.quantum.registry.QAItems;

public class MessageChangeFocus implements IMessage, IMessageHandler<MessageChangeFocus, IMessage> {

    public MessageChangeFocus() {}

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(MessageChangeFocus message, MessageContext ctx) {
        ItemSceptre sceptre = (ItemSceptre) QAItems.SCEPTRE.getItem();
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        ItemStack use = player.getCurrentEquippedItem();

        if (use != null && use.getItem() == sceptre) {
            sceptre.tryFocusChange(player, use);
        }
        return null;
    }
}
