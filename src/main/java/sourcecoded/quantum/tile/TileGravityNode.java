package sourcecoded.quantum.tile;

import net.minecraft.tileentity.TileEntity;
import sourcecoded.quantum.api.gravity.GravityHandler;
import sourcecoded.quantum.client.renderer.fx.helpers.FXManager;

public class TileGravityNode extends TileEntity {

    public GravityHandler gravityHandler = new GravityHandler(0.05F);

    public void updateEntity() {
        super.updateEntity();

        gravityHandler.onUpdate(this);

        if (this.worldObj.isRemote) {
            FXManager.portalFX1Fragment(worldObj, xCoord, yCoord, zCoord);
            if (this.worldObj.rand.nextInt(9) == 0) {
                FXManager.portalFX1Hole(0.5F, worldObj, xCoord, yCoord, zCoord);
                FXManager.portalFX2Filler(0.5F, worldObj, xCoord, yCoord, zCoord);

            }
        }
    }

}
