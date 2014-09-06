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

public class FXPortalFragment extends EntityFX {

    public static class DataHolder {

        public float red = 1F;
        public float green = 1F;
        public float blue = 1F;

        public float minBrightness = 0F;
        public float maxBrightness = 0.3F;

        public float fadeLength = 0.25F;

        public float size = 1F;

        public float xRadius = 0F;
        public float yRadius = 0.5F;
        public float zRadius = 0F;

        public DataHolder() {}
        public DataHolder(float r, float g, float b, float bright, float size) {
            this.red = r;
            this.green = g;
            this.blue = b;

            this.maxBrightness = bright;
            this.size = size;
        }

    }

    FadeHelper brightnessFade;

    public DataHolder data;
    public static ResourceLocation tex = new ResourceLocation(Constants.MODID, "textures/misc/particle/riftNode.png");

    float brightness = 0F;

    float rotation;

    public FXPortalFragment(World world, double x, double y, double z, DataHolder data) {
        super(world, x, y, z);
        this.data = data;

        setupFX();
        brightnessFade = new FadeHelper(data.minBrightness, data.maxBrightness, particleMaxAge, data.fadeLength);
    }

    public void setupFX() {
        this.particleRed = data.red;
        this.particleGreen = data.green;
        this.particleBlue = data.blue;
        this.particleGravity = 0.0f;
        this.particleMaxAge = ((int)(24.0D / (Math.random() * 0.3D + 0.7D)));
        this.noClip = false;
    }

    public int getFXLayer() {
        return 0;
    }


    public void renderParticle(Tessellator tess, float ptt, float rotX, float rotXZ, float rotZ, float rotYZ, float rotXY) {
        if (data.fadeLength != 0F)
            brightness = brightnessFade.updateFade(particleAge);
        else brightness = data.maxBrightness;

        float percentage = (float)particleAge / (float)particleMaxAge;

        EntityLivingBase renderEntity = FMLClientHandler.instance().getClient().thePlayer;

        tess.draw();

        glPushMatrix();

        glDepthMask(false);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(tex);

        float scale = data.size;

        float[] pos = FXHelper.trackingParticleLocale(this, ptt);
        float[] rot = new float[] {rotX, rotXZ, rotZ, rotYZ, rotXY};

        pos[0] += (data.xRadius / 2F) * (Math.cos(2 * Math.PI * percentage)) + (data.xRadius / 2F);
        pos[1] += (data.yRadius / 2F) * (Math.cos(2 * Math.PI * percentage)) + (data.yRadius / 2F);
        pos[2] += (data.zRadius / 2F) * (Math.cos(2 * Math.PI * percentage)) + (data.zRadius / 2F);

        tess.startDrawingQuads();
        tess.setBrightness(240);

        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, brightness);

        FXHelper.drawTrackingParticle(tess, pos, scale, rot);
        tess.draw();

        glDisable(GL_BLEND);
        glDepthMask(true);

        glPopMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(FXHelper.getParticleTexture());

        tess.startDrawingQuads();
    }

}
