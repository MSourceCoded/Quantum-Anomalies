package sourcecoded.quantum.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import scala.tools.nsc.backend.icode.Members;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.discovery.*;
import sourcecoded.quantum.api.event.discovery.DiscoveryRegistrationEvent;
import sourcecoded.quantum.api.event.discovery.DiscoveryUpdateEvent;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class GuiDiscoveryCategory extends GuiScreen {

    public static ResourceLocation guiLocation = new ResourceLocation(Constants.MODID, "textures/gui/discoveries.png");
    public ResourceLocation guiBackground = new ResourceLocation(Constants.MODID, "textures/gui/discoveriesBackground.png");
    public static ResourceLocation guiBlank = new ResourceLocation(Constants.MODID, "textures/misc/blank.png");
    public int frameWidth = 256;
    public int frameHeight = 202;

    int lastMouseX;
    int lastMouseY;

    boolean mouseDone;

    int dragX = 200;
    int dragY = 30;

    List<DiscoveryItem> items;

    public EntityPlayer player;

    boolean mouse = true;

    public GuiDiscoveryCategory(DiscoveryCategory category, EntityPlayer player) {
        this.player = player;

        items = new ArrayList<DiscoveryItem>();
        for (Map.Entry<String, DiscoveryItem> entry : category.discoveries.entrySet()) {
            if (!DiscoveryManager.itemHidden(entry.getKey(), player)) {
                items.add(entry.getValue());
            }
        }

        if (category.background != null)
            this.guiBackground = category.background;
    }

    public void onGuiClosed() {
    }

    public void keyTyped(char Char, int par2) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_E)) {
            if (isShiftKeyDown())
                this.mc.displayGuiScreen(null);
            else
                this.mc.displayGuiScreen(new GuiDiscoveryMain(player));
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawScreen(int mx, int my, float par3) {
        this.mc.getTextureManager().bindTexture(guiBlank);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
        GL11.glColor4f(0F, 0F, 0F, 0.1F);
        this.drawTexturedModalRect(0, 0, 0, 0, this.width, this.height);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        int centreW = super.width / 2;
        int centreH = super.height / 2;

        Tessellator tess = Tessellator.instance;

        int pad = 10;
        int minDragX = -10;
        int minDragY = 0;
        int maxDragX = 400;
        int maxDragY = 180;

        if (Mouse.isButtonDown(0)) {
            if (!mouseDone) {
                int xOffset = mx - lastMouseX;
                int yOffset = my - lastMouseY;

                xOffset /= 2;
                yOffset /= 1;

                dragX = Math.min(Math.max(minDragX, dragX - xOffset), maxDragX);
                dragY = Math.min(Math.max(minDragY, dragY - yOffset), maxDragY);
            }

            lastMouseX = mx;
            lastMouseY = my;

            mouseDone = false;
        } else {
            mouseDone = true;
        }

        int px = centreW - frameWidth / 2 + pad;
        int py = centreH - frameHeight / 2 + pad;

        int w = frameWidth - pad * 2;
        int h = frameHeight - pad * 3;

        int dragCo = 4;

        float scale = 1.5F;
        float scaleI = (float) Math.pow(scale, -1);

        GL11.glScalef(scale, scale, 1F);
        this.mc.getTextureManager().bindTexture(guiBackground);
        this.drawTexturedModalRect((int) (px / scale), (int) (py / scale), dragX / dragCo, dragY / dragCo, (int) (w / scale), (int) (h / scale));           //Rendered with parallax
        GL11.glScalef(scaleI, scaleI, 1F);

        int perOffsetX = 40;
        int perOffsetY = 40;

        int itemDiameter = 22;

        GL11.glDepthFunc(GL11.GL_GEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -200.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();

        GL11.glScalef(scale, scale, 1F);
        this.drawTexturedModalRect((int) (px / scale), (int) (py / scale), dragX / dragCo, dragY / dragCo, (int) (w / scale), (int) (h / scale));           //Rendered with parallax
        GL11.glScalef(scaleI, scaleI, 1F);

        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        for (DiscoveryItem item : items) {              //Draw the Connections before the items
            int itemX = centreW + item.getX() - dragX;
            int itemY = centreH - 38 - dragY + item.getY();

            GL11.glEnable(GL11.GL_BLEND);

            boolean unlocked = DiscoveryManager.itemUnlocked(item.getKey(), player);

            for (String parent : item.parents) {
                if (DiscoveryRegistry.getCategoryForItem(parent) == DiscoveryRegistry.getCategoryForItem(item.getKey())) {
                    DiscoveryItem parentItem = DiscoveryRegistry.getItemFromKey(parent);

                    int pitemX = centreW + parentItem.getX() - dragX;
                    int pitemY = centreH - 38 - dragY + parentItem.getY();

                    drawLine(itemX, itemY, pitemX, pitemY, item.getParentColour().rgb[0], item.getParentColour().rgb[1], item.getParentColour().rgb[2], parentItem.getChildColour().rgb[0], parentItem.getChildColour().rgb[1], parentItem.getChildColour().rgb[2], !unlocked);
                }
            }

            GL11.glDisable(GL11.GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        for (DiscoveryItem item : items) {
            int renderCode = 0;

            if (item instanceof IDiscoveryCustomRenderer)
                renderCode = ((IDiscoveryCustomRenderer) item).getRenderContext();

            boolean unlocked = DiscoveryManager.itemUnlocked(item.getKey(), player);

            if (unlocked)
                GL11.glColor4f(1F, 1F, 1F, 1F);
            else
                GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);

            GL11.glEnable(GL11.GL_BLEND);

            int itemX = centreW - (itemDiameter / 2) + item.getX() - dragX;
            int itemY = centreH - (itemDiameter) / 2 - 38 - dragY + item.getY();

            if (renderCode != 2 && renderCode != 3) {
                this.mc.getTextureManager().bindTexture(guiLocation);
                //drawTexturedModalRect(centreW - 13 + xFactor - dragX, centreH - 46 - dragY + yOffset, 26, 202, 26, 26);
                if (item.getSpecial())
                    drawPartialQuadWithBounds(itemX, itemY, itemDiameter, itemDiameter, 26F / 256F, 202F / 256F, 52F / 256F, 228F / 256F);
                else
                    drawPartialQuadWithBounds(itemX, itemY, itemDiameter, itemDiameter, 0F / 256F, 202F / 256F, 26F / 256F, 228F / 256F);
            }

            if (mx >= itemX && mx <= itemX + itemDiameter && my >= itemY && my <= itemY + itemDiameter) {
                List<String> list = new ArrayList<String>();
                List<String> desc = new ArrayList<String>();
                desc.add(LocalizationUtils.translateLocalWithColours(item.getUnlocalizedDescription(), item.getUnlocalizedDescription()));
                desc = LocalizationUtils.translateList(desc);               //Split the description with <br> breaks

                list.add(EnumChatFormatting.BLUE + item.getLocalizedName());
                list.addAll(desc);
                tooltip(list, mx, my);

                if (Mouse.isButtonDown(0) && !mouse && unlocked && item.pages != null && item.pages.size() > 0) {
                    //CLICKED
                    this.mc.displayGuiScreen(new GuiDiscoveryPage(item, 0, player, this));
                }
            }

            RenderItem render = new RenderItem();

            if (renderCode != 1 && renderCode != 3) {
                if (!unlocked)
                    GL11.glColor4f(0.3F, 0.3F, 0.3F, 1F);
                if (item.icon != null) {
                    this.mc.getTextureManager().bindTexture(item.icon);
                    drawFullQuadWithBounds(centreW - 8 + item.getX() - dragX, centreH - 46 - dragY + item.getY(), 16, 16);
                } else if (item.displayStack != null) {
                    if (!unlocked)
                        GL11.glEnable(GL_LIGHTING);
                    render.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), item.displayStack, centreW - 8 + item.getX() - dragX, centreH - 46 - dragY + item.getY());
                    GL11.glDisable(GL_LIGHTING);
                }
            }

            if (renderCode != 0)
                ((IDiscoveryCustomRenderer) item).render(item.getX() - dragX, dragY + item.getY(), mx, my, renderCode);

            itemX = centreW + item.getX() - dragX;
            itemY = centreH - 23 - dragY + item.getY();

            GL11.glDisable(GL11.GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(guiLocation);
        this.drawTexturedModalRect(centreW - frameWidth / 2, centreH - frameHeight / 2, 0, 0, frameWidth, frameHeight);

        renderTooltips();

        super.drawScreen(mx, my, par3);

        GL11.glPopMatrix();

        mouse = Mouse.isButtonDown(0);
    }

    public void drawLine(int x, int y, int x2, int y2, float r, float g, float b, float r2, float g2, float b2, boolean dim) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glLineWidth(3);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glBegin(GL11.GL_LINES);

        float bright1 = (float) Math.sin(GlowRenderHandler.instance().scaler * 15) / 4F + 0.35F;
        float bright2 = (float) Math.cos(GlowRenderHandler.instance().scaler * 15) / 4F + 0.35F;

        if (dim) {
            bright1 /= 5;
            bright2 /= 3;
        }

        GL11.glColor4f(r, g, b, bright1);
        GL11.glVertex3f(x, y, 0);
        GL11.glColor4f(r2, g2, b2, bright2);
        GL11.glVertex3f(x2, y2, 0);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    List<Tooltip> tips = new ArrayList<Tooltip>();

    public void tooltip(List text, int x, int y) {
        tips.add(new Tooltip(text, x, y));
    }

    public void renderTooltips() {
        for (Tooltip tip : tips)
            this.drawHoveringText(tip.text, tip.x, tip.y, fontRendererObj);

        tips.clear();
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