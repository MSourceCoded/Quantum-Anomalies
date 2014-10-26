package sourcecoded.quantum.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.api.discovery.DiscoveryItem;
import sourcecoded.quantum.api.discovery.DiscoveryRegistry;
import sourcecoded.quantum.api.discovery.IDiscoveryCustomRenderer;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import static org.lwjgl.opengl.GL11.*;

public class GuiDiscoveryUnlocked extends Gui {

    private static final ResourceLocation texAchi = new ResourceLocation("textures/gui/achievement/achievement_background.png");

    public Minecraft mc = Minecraft.getMinecraft();

    private int width;

    private int height;

    private long unlockedTime;

    public DiscoveryItem item;

    public String title;
    public String body;

    public RenderItem render = new RenderItem();

    public void setItem(DiscoveryItem item) {
        String titleLoc = "qa.journal.item.unlocked";
        this.title = LocalizationUtils.translateLocalWithColours(titleLoc, titleLoc);

        this.body = item.getLocalizedName();

        this.unlockedTime = Minecraft.getSystemTime();

        this.item = item;
    }

    public void setItem(String itemName) {
        this.setItem(DiscoveryRegistry.getItemFromKey(itemName));
    }

    private void updateWindowScale() {
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.width = scaledresolution.getScaledWidth();
        this.height = scaledresolution.getScaledHeight();
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double)this.width, (double)this.height, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }

    public void updateGui() {
        if (this.item != null && this.unlockedTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
            double d0 = (double)(Minecraft.getSystemTime() - this.unlockedTime) / 3000.0D;

            if (d0 < 0.0D || d0 > 1.0D) {
                this.unlockedTime = 0L;
                return;
            }

            this.updateWindowScale();
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            double d1 = d0 * 2.0D;

            if (d1 > 1.0D)
                d1 = 2.0D - d1;

            d1 *= 4.0D;
            d1 = 1.0D - d1;

            if (d1 < 0.0D)
                d1 = 0.0D;

            d1 *= d1;
            d1 *= d1;
            int i = this.width - 160;
            int j = 0 - (int)(d1 * 36.0D);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            this.mc.getTextureManager().bindTexture(texAchi);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.drawTexturedModalRect(i, j, 96, 202, 160, 32);

            float scale = 0.75F;
            float scaleI = (float) Math.pow(scale, -1);

            this.mc.fontRenderer.drawString(EnumChatFormatting.LIGHT_PURPLE + this.title, i + 30, j + 7, -1);
            GL11.glScalef(scale, scale, 1F);
            this.mc.fontRenderer.drawString(this.body, (int)((i + 30) / scale), (int)((j + 18) / scale), -1);
            GL11.glScalef(scaleI, scaleI, 1F);

            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);

            glEnable(GL_DEPTH_TEST);
            glDepthMask(true);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            int renderCode = 0;

            if (item instanceof IDiscoveryCustomRenderer)
                renderCode = ((IDiscoveryCustomRenderer) item).getRenderContext();

            if (renderCode != 1 && renderCode != 3) {
                if (item.icon != null) {
                    this.mc.getTextureManager().bindTexture(item.icon);
                    drawFullQuadWithBounds(i + 8, j + 8, 16, 16);
                } else if (item.displayStack != null) {
                    render.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), item.displayStack, i + 8, j + 8);
                    GL11.glDisable(GL_LIGHTING);
                }
            }

            if (renderCode != 0)
                ((IDiscoveryCustomRenderer) item).render(i + 8, j + 8, i, j, renderCode);

            glDisable(GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
    }

    public void drawFullQuadWithBounds(int x, int y, int width, int height) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, this.zLevel, 0, 1);
        tess.addVertexWithUV(x + width, y + height, this.zLevel, 1, 1);
        tess.addVertexWithUV(x + width, y, this.zLevel, 1, 0);
        tess.addVertexWithUV(x, y, this.zLevel, 0, 0);
        tess.draw();
    }

    public void drawPartialQuadWithBounds(int x, int y, int width, int height, float u, float v, float U, float V) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, this.zLevel, u, V);
        tess.addVertexWithUV(x + width, y + height, this.zLevel, U, V);
        tess.addVertexWithUV(x + width, y, this.zLevel, U, v);
        tess.addVertexWithUV(x, y, this.zLevel, u, v);
        tess.draw();
    }
}