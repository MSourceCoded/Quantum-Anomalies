package sourcecoded.quantum.client.renderer.fx.helpers;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class FXHelper {

    public static ResourceLocation getParticleTexture() {
        try {
            return (ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "b", "field_110737_b");
        }
        catch (Exception e) {}
        return null;
    }

    /**
     * Find the altered locale of the particle for rendering
     */
    public static float[] trackingParticleLocale(Entity ent, float ptt) {
        float x = (float) (ent.prevPosX + (ent.posX - ent.prevPosX) * ptt - EntityFX.interpPosX);
        float y = (float) (ent.prevPosY + (ent.posY - ent.prevPosY) * ptt - EntityFX.interpPosY);
        float z = (float) (ent.prevPosZ + (ent.posZ - ent.prevPosZ) * ptt - EntityFX.interpPosZ);

        return new float[] {x, y, z};
    }

    /**
     * Draw a particle facing the camera.
     *
     * Probably the worst code I've ever written
     */
    public static void drawTrackingParticle(Tessellator tess, float[] particleLocale, float scale, float[] rotation) {
        tess.addVertexWithUV((particleLocale[0] - rotation[0] * scale - rotation[3] * scale), (particleLocale[1] - rotation[1] * scale), (particleLocale[2] - rotation[2] * scale - rotation[4] * scale), 0.0D, 1.0D);
        tess.addVertexWithUV((particleLocale[0] - rotation[0] * scale + rotation[3] * scale), (particleLocale[1] + rotation[1] * scale), (particleLocale[2] - rotation[2] * scale + rotation[4] * scale), 1.0D, 1.0D);
        tess.addVertexWithUV((particleLocale[0] + rotation[0] * scale + rotation[3] * scale), (particleLocale[1] + rotation[1] * scale), (particleLocale[2] + rotation[2] * scale + rotation[4] * scale), 1.0D, 0.0D);
        tess.addVertexWithUV((particleLocale[0] + rotation[0] * scale - rotation[3] * scale), (particleLocale[1] - rotation[1] * scale), (particleLocale[2] + rotation[2] * scale - rotation[4] * scale), 0.0D, 0.0D);
    }

}
