package sourcecoded.quantum.client.renderer.block;

import net.minecraft.tileentity.TileEntity;

public interface IBlockRenderHook {

    public void callbackInventory(TileEntity tile);

}
