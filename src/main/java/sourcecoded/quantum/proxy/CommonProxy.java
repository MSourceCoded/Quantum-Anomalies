package sourcecoded.quantum.proxy;

import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy implements IProxy {

    @Override
    public void register() {
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return null;
    }

}
