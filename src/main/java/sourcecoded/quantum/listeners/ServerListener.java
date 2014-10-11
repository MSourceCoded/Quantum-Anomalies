package sourcecoded.quantum.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import sourcecoded.quantum.util.save.QAWorldSavedData;

public class ServerListener {

    @SubscribeEvent(receiveCanceled = true)
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            QAWorldSavedData.getInstance(event.player.getEntityWorld()).markForUpdate(event.player.getEntityWorld());
    }

}
