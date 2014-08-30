package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.utils.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRInfusedStone extends TileEntitySpecialRenderer {

    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/haze.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
        glPushMatrix();
        glTranslated(x, y, z);

        glEnable(GL_BLEND);
        glDisable(GL_LIGHTING);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float cubeSize = 0.45F;
        float innerPadding = 0.05F;

        Tessellator tess = Tessellator.instance;


        tess.startDrawingQuads();
        this.bindTexture(texDark);
        glColor4f(1F, 1F, 1F, 1F);
        tess.setColorRGBA_F(1F, 1F, 1F, 1F);

        TessUtils.drawCube(tess, 0, 0, 0, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 1F - cubeSize, 0, 0, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 1F - cubeSize, 1F - cubeSize, 0, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 0, 1F - cubeSize, 0, cubeSize, 0, 0, 0.5, 0.5);

        TessUtils.drawCube(tess, 0, 0, 1F - cubeSize, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 1F - cubeSize, 0, 1F - cubeSize, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 1F - cubeSize, 1F - cubeSize, 1F - cubeSize, cubeSize, 0, 0, 0.5, 0.5);
        TessUtils.drawCube(tess, 0, 1F - cubeSize, 1F - cubeSize, cubeSize, 0, 0, 0.5, 0.5);
        tess.draw();

        tess.startDrawingQuads();
        tess.setColorRGBA_F(1F, 1F, 1F, GlowRenderHandler.instance().brightness);
        tess.setBrightness(240);
        this.bindTexture(texHaze);
        TessUtils.drawCube(tess, innerPadding, innerPadding, innerPadding, 1F - (innerPadding * 2), 0, 0, 1, 1);
        tess.draw();

        glDisable(GL_BLEND);
        glEnable(GL_LIGHTING);
        glPopMatrix();
    }
}
