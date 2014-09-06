package sourcecoded.quantum.client.renderer.fx;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.fx.helpers.FXHelper;
import sourcecoded.quantum.client.renderer.fx.helpers.FadeHelper;

import static org.lwjgl.opengl.GL11.*;

public class FXRiftNode extends EntityFX {

    public static class DataHolder {

        public float red = 1F;
        public float green = 1F;
        public float blue = 1F;

        public float minxBrightness = 0F;
        public float maxBrightness = 0.5F;

        public float fadeLength = 0.25F;

        public float size = 1F;

        public DataHolder() {}
        public DataHolder(float r, float g, float b, float bright, float size) {
            this.red = r;
            this.green = g;
            this.blue = b;

            this.maxBrightness = bright;
            this.size = size;
        }

    }

    public FadeHelper brightnessFade;

    public DataHolder data;
    public static ResourceLocation tex = new ResourceLocation(Constants.MODID, "textures/misc/particle/riftNode.png");
    public static ResourceLocation texH = new ResourceLocation(Constants.MODID, "textures/misc/particle/hole.png");

    float brightness = 0F;

    public boolean hollow;

    public FXRiftNode(World world, double x, double y, double z, DataHolder data, boolean hollow) {
        super(world, x, y, z);
        this.data = data;
        this.hollow = hollow;


        setupFX();
        if (hollow) particleMaxAge = 100;
        brightnessFade = new FadeHelper(data.minxBrightness, data.maxBrightness, particleMaxAge, data.fadeLength);
    }

    public void setupFX() {
        this.particleRed = data.red;
        this.particleGreen = data.green;
        this.particleBlue = data.blue;
        this.particleGravity = 0.0f;
        this.particleMaxAge = ((int)(36.0D / (Math.random() * 0.3D + 0.7D)));
        this.noClip = false;
    }

    public int getFXLayer() {
        return 0;
    }


    public void renderParticle(Tessellator tess, float ptt, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
        brightness = brightnessFade.updateFade(particleAge);

        EntityLivingBase renderEntity = FMLClientHandler.instance().getClient().thePlayer;

        tess.draw();

        glPushMatrix();

        glDepthMask(false);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if (!hollow)
            Minecraft.getMinecraft().renderEngine.bindTexture(tex);
        else
            Minecraft.getMinecraft().renderEngine.bindTexture(texH);

        float scale = data.size;

        float[] pos = FXHelper.trackingParticleLocale(this, ptt);
        float[] rot = new float[] {rotX, rotXZ, rotZ, rotYZ, rotXY};

        draw(tess, pos, scale, rot);

        glDisable(GL_BLEND);
        glDepthMask(true);

        glPopMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(FXHelper.getParticleTexture());

        tess.startDrawingQuads();
    }

    public void draw(Tessellator tess, float[] pos, float scale, float[] rot) {
        tess.startDrawingQuads();
        tess.setBrightness(240);

        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, brightness);

        FXHelper.drawTrackingParticle(tess, pos, scale, rot);

        tess.draw();
    }

}
