package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public abstract class TESRStaticHandler extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float ptt) {
        renderTile(tile, x, y, z, ptt, false);
    }

    /**
     * Renders the TileEntity
     * ptt will be 0 if isStatic, as it is a static render
     */
    public abstract void renderTile(TileEntity te, double x, double y, double z, float ptt, boolean isStatic);

}
