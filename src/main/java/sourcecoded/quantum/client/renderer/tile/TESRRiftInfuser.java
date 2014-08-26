package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileRiftInfuser;
import sourcecoded.quantum.utils.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRRiftInfuser extends TESRStaticHandler {

    WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/block/infuser.obj"));
    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/haze.png");

    @Override
    public void renderTile(TileEntity te, double x, double y, double z, float ptt, boolean isStatic) {
        if (!isStatic) {
            glPushMatrix();
            glTranslated(x, y, z);

            glEnable(GL_BLEND);
            glDisable(GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            Tessellator tess = Tessellator.instance;

            tess.startDrawingQuads();
            tess.setColorRGBA_F(1F, 1F, 1F, GlowRenderHandler.instance().brightness);
            tess.setBrightness(240);
            this.bindTexture(texHaze);

            float percentage = 0.01F;

            if (te != null && te instanceof TileRiftInfuser) {
                try {
                    percentage = (float)((TileRiftInfuser) te).getRiftEnergy() / (float)((TileRiftInfuser) te).getMaxRiftEnergy();
                } catch (ArithmeticException exception) {
                    //Divided by 0
                }
            }

            float minimum = 4F/16F;

            float height = percentage * 4.5F/16F + minimum;
            TessUtils.drawFace(ForgeDirection.UP, tess, 1/16F, height, 1/16F, 1-1/16F, height, 1-1/16F, 0, 0, 1, 1);

            tess.draw();

            glEnable(GL_LIGHTING);
            glPopMatrix();
        } else {
            Tessellator tess = Tessellator.instance;

            x += 0.5;
            z += 0.5;

            tess.addTranslation((float) x, (float) y, (float) z);
            tess.startDrawingQuads();
            Minecraft.getMinecraft().renderEngine.bindTexture(texDark);
            model.tessellatePart(tess, "Base");
            tess.draw();
            tess.addTranslation((float) -x, (float) -y, (float) -z);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
    }

}
