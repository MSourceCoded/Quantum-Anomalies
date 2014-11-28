package sourcecoded.quantum.proxy;

import net.minecraft.entity.player.EntityPlayer;

public interface IProxy {

    public void register();

    public EntityPlayer getClientPlayer();

}
