package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.block.BlockInjectedGlass;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileInjectedGlass;
import sourcecoded.quantum.utils.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRInjectedGlass extends TileEntitySpecialRenderer {

    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/glassBlack.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/glassPurpleDesaturated.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
        glPushMatrix();
        glTranslated(x, y, z);

        glEnable(GL_BLEND);
        glDisable(GL_LIGHTING);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tess = Tessellator.instance;

        tess.startDrawingQuads();
        this.bindTexture(texDark);
        draw(tess, te);
        tess.draw();

        tess.startDrawingQuads();
        //tess.setColorRGBA_F(1F, 1F, 1F, GlowRenderHandler.instance().brightness);

        float[] rgb = ((TileInjectedGlass) te).colour.rgb;

        tess.setColorRGBA_F(rgb[0], rgb[1], rgb[2], GlowRenderHandler.instance().brightness);

        tess.setBrightness(240);
        this.bindTexture(texHaze);
        draw(tess, te);
        tess.draw();

        glDisable(GL_BLEND);
        glEnable(GL_LIGHTING);
        glPopMatrix();
    }

    void draw(Tessellator tess, TileEntity te) {
        if (te.hasWorldObj()) {
            ForgeDirection currentDirection = ForgeDirection.UP;
            boolean top = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;
            currentDirection = ForgeDirection.DOWN;
            boolean bottom = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;
            currentDirection = ForgeDirection.NORTH;
            boolean north = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;
            currentDirection = ForgeDirection.SOUTH;
            boolean south = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;
            currentDirection = ForgeDirection.EAST;
            boolean east = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;
            currentDirection = ForgeDirection.WEST;
            boolean west = te.getWorldObj().getBlock(te.xCoord + currentDirection.offsetX, te.yCoord + currentDirection.offsetY, te.zCoord + currentDirection.offsetZ) instanceof BlockInjectedGlass;

            if (!top) TessUtils.drawFace(ForgeDirection.UP, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
            if (!bottom) TessUtils.drawFace(ForgeDirection.DOWN, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
            if (!north) TessUtils.drawFace(ForgeDirection.NORTH, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
            if (!south) TessUtils.drawFace(ForgeDirection.SOUTH, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
            if (!east) TessUtils.drawFace(ForgeDirection.EAST, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
            if (!west) TessUtils.drawFace(ForgeDirection.WEST, tess, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1);
        } else
            TessUtils.drawCube(tess, 0, 0, 0, 1, 0, 0, 1, 1);
    }

}
