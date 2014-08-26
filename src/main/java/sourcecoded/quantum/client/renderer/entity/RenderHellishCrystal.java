package sourcecoded.quantum.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.entity.EntityHellishCrystal;

@SideOnly(Side.CLIENT)
public class RenderHellishCrystal extends Render {

    private static final ResourceLocation texture = new ResourceLocation(Constants.MODID, "textures/entity/hellishCrystal.png");
    private ModelBase field_76995_b;
    private static final String __OBFID = "CL_00000987";

    public RenderHellishCrystal() {
        this.shadowSize = 0.5F;
        this.field_76995_b = new ModelEnderCrystal(0.0F, true);
    }

    public void doRender(EntityHellishCrystal p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        float f2 = (float) p_76986_1_.innerRotation + p_76986_9_;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
        this.bindTexture(texture);
        float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
        f3 += f3 * f3;
        this.field_76995_b.render(p_76986_1_, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityHellishCrystal p_110775_1_) {
        return texture;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityHellishCrystal) p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityHellishCrystal) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}