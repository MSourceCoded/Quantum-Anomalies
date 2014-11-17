package sourcecoded.quantum.client.renderer;

import com.google.common.collect.HashBiMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.api.Point3D;
import sourcecoded.quantum.handler.ConfigHandler;
import sourcecoded.quantum.util.save.QAWorldSavedData;

import java.util.HashMap;
import java.util.Map;

public enum WorldLabelRenderer {
    INSTANCE;

    HashMap<Point3D, String> labels;

    double renderDistance = 64F;

    FontRenderer fontrenderer;
    RenderManager renderManager;

    public void init() {
        renderDistance = ConfigHandler.getDouble(ConfigHandler.Properties.BLOCK_LABEL_DISTANCE);
    }

    public void update(QAWorldSavedData data, World world) {
        labels = data.getLabels(world);
    }

    public void startRender(float ptt) {
        Minecraft mc = Minecraft.getMinecraft();

        EntityPlayer player = mc.thePlayer;

        fontrenderer = mc.fontRenderer;
        renderManager = RenderManager.instance;

        if (player != null && labels != null)
            for (Map.Entry<Point3D, String> entry : labels.entrySet()) {
                String name = entry.getValue();
                Point3D point = entry.getKey();

                if (player.getDistanceSq(point.getX(), point.getY(), point.getZ()) <= renderDistance)
                    renderTag(name, point, player, ptt);
            }
    }

    public void renderTag(String tag, Point3D point, EntityPlayer player, float ptt) {
        float f = 1.6F;
        float f1 = 0.016666668F * f;

        //Interpolate for clean animation
        float x = (float) (point.getX() - (player.prevPosX + (player.posX - player.prevPosX) * ptt));
        float y = (float) (point.getY() - (player.prevPosY + (player.posY - player.prevPosY) * ptt));
        float z = (float) (point.getZ() - (player.prevPosZ + (player.posZ - player.prevPosZ) * ptt));

        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + 0.5F), (float)(y + 1.5F), (float)(z + 0.5F));
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        int ij = fontrenderer.getStringWidth(tag) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex((double)(-ij - 1), -1.0D, 0.0D);
        tessellator.addVertex((double)(-ij - 1), 8.0D, 0.0D);
        tessellator.addVertex((double)(ij + 1), 8.0D, 0.0D);
        tessellator.addVertex((double)(ij + 1), -1.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
        fontrenderer.drawString(tag, -fontrenderer.getStringWidth(tag) / 2, 0, 16777215);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

}