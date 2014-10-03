package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileManipulation;
import sourcecoded.quantum.util.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRManipulation extends TESRStaticHandler {

    WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/block/matterManipulator.obj"));
    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/hazeDesaturated.png");

    @Override
    public void renderTile(TileEntity te, double x, double y, double z, float ptt, boolean isStatic, RenderBlocks renderBlocks) {
        if (!isStatic) {
            glPushMatrix();
            glTranslated(x, y, z);

            glEnable(GL_BLEND);
            glDisable(GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            Tessellator tess = Tessellator.instance;

            float innerPadding = 0.025F;

            tess.startDrawingQuads();

            float[] rgb = ((TileManipulation) te).colour.rgb;

            tess.setColorRGBA_F(rgb[0], rgb[1], rgb[2], GlowRenderHandler.instance().brightness);

            tess.setBrightness(240);
            this.bindTexture(texHaze);
            TessUtils.drawCube(tess, innerPadding, innerPadding, innerPadding, 1F - (innerPadding * 2), 0, 0, 1, 1);
            tess.draw();

            glDisable(GL_BLEND);
            glEnable(GL_LIGHTING);
            glPopMatrix();
        } else {
            Tessellator tess = Tessellator.instance;

            x += 0.5;
            z += 0.5;

            tess.addTranslation((float)x, (float)y, (float)z);
            tess.startDrawingQuads();
            tess.setColorRGBA_F(1F, 1F, 1F, 1F);
            brightness(tess);
            Minecraft.getMinecraft().renderEngine.bindTexture(texDark);
            model.tessellateAll(tess);
            tess.draw();
            tess.addTranslation((float)-x, (float)-y, (float)-z);

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
    }

}
