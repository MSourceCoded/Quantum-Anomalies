package sourcecoded.quantum.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.client.renderer.fx.helpers.FXHelper;
import sourcecoded.quantum.entity.EntityEnergyPacket;

import static org.lwjgl.opengl.GL11.*;

public class RenderEnergyPacket extends Render {

    ResourceLocation tex = new ResourceLocation(Constants.MODID, "textures/misc/particle/riftNode.png");

    @Override
    public void doRender(Entity entity, double x, double y, double z, float fq, float ptt) {
        EntityEnergyPacket ent = (EntityEnergyPacket) entity;

        Tessellator tess = Tessellator.instance;

        glPushMatrix();

        glDepthMask(false);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(tex);

        float scale = 0.1F;

        float[] pos = {(float) x, (float) y, (float) z};
        float[] rot = new float[] {ActiveRenderInfo.rotationX, ActiveRenderInfo.rotationXZ, ActiveRenderInfo.rotationZ, ActiveRenderInfo.rotationYZ, ActiveRenderInfo.rotationXY};

        tess.startDrawingQuads();
        tess.setBrightness(240);

        //tess.setColorRGBA_F(1F, 0F, 1F, 0.5F);

        Colourizer colour = ent.getColour();

        tess.setColorRGBA_F(colour.rgb[0], colour.rgb[1], colour.rgb[2], 0.3F);

        FXHelper.drawTrackingParticle(tess, pos, scale, rot);

        tess.draw();

        glDisable(GL_BLEND);
        glDepthMask(true);

        glPopMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(FXHelper.getParticleTexture());

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return AbstractClientPlayer.locationStevePng;
    }
}
