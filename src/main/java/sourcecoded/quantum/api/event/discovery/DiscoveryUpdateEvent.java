package sourcecoded.quantum.api.event.discovery;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Fired when something in the DiscoveryManager changes,
 * i.e. syncing Client/Server changes
 *
 * @author SourceCoded
 */
public class DiscoveryUpdateEvent extends Event {

    public EntityPlayer player;

    public DiscoveryUpdateEvent(EntityPlayer player) {
        this.player = player;
    }

}
