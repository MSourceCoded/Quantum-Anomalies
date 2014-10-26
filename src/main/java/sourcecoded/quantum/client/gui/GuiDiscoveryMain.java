package sourcecoded.quantum.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.discovery.DiscoveryCategory;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.api.discovery.DiscoveryRegistry;
import sourcecoded.quantum.api.discovery.IDiscoveryCustomRenderer;
import sourcecoded.quantum.api.event.discovery.DiscoveryUpdateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class GuiDiscoveryMain extends GuiScreen {

    public static ResourceLocation guiLocation = new ResourceLocation(Constants.MODID, "textures/gui/discoveries.png");
    public static ResourceLocation guiBackground = new ResourceLocation(Constants.MODID, "textures/gui/discoveriesBackground.png");
    public static ResourceLocation guiBlank = new ResourceLocation(Constants.MODID, "textures/misc/blank.png");
    public int frameWidth = 256;
    public int frameHeight = 202;

    int lastMouseX;
    int lastMouseY;

    boolean mouseDone;

    int dragX = 0;
    int dragY = 0;

    List<List<DiscoveryCategory>> categorySets;

    public EntityPlayer player;

    boolean mouse = true;

    public GuiDiscoveryMain(EntityPlayer player) {
        QuantumAPI.eventBus.post(new DiscoveryUpdateEvent(player));

        this.player = player;

        categorySets = new ArrayList<List<DiscoveryCategory>>();
        int index = 0;
        int level = 0;
        for (Map.Entry<String, DiscoveryCategory> entry : DiscoveryRegistry.categories.entrySet()) {
            if (index == 4) {
                level++;
                index = 0;
            }
            if (categorySets.size() == level || categorySets.get(level) == null)
                categorySets.add(new ArrayList<DiscoveryCategory>());
            if (!DiscoveryManager.categoryHidden(entry.getKey(), player)) {
                categorySets.get(level).add(entry.getValue());
                index++;
            }
        }
    }

    public void keyTyped(char Char, int par2) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_E)) {
            this.mc.displayGuiScreen(null);
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
        int maxDragX = 30;
        int maxDragY = 90;

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

        this.mc.getTextureManager().bindTexture(guiBackground);
        this.drawTexturedModalRect(centreW - frameWidth / 2 + pad, centreH - frameHeight / 2 + pad, dragX / 3, dragY / 3, frameWidth - pad * 2, frameHeight - pad * 3);           //Rendered with parallax

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

        this.drawTexturedModalRect(centreW - frameWidth / 2 + pad, centreH - frameHeight / 2 + pad, dragX / 3, dragY / 3, frameWidth - pad * 2, frameHeight - pad * 3);           //Rendered with parallax

        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        for (List<DiscoveryCategory> cat : categorySets) {
            int totalOffset = perOffsetX * (cat.size() + 1);
            int yOffset = perOffsetY * categorySets.indexOf(cat);
            for (DiscoveryCategory meow : cat) {            //Ha.... ha.... ha.....
                int xFactor = perOffsetX * (cat.indexOf(meow) + 1);
                xFactor -= (totalOffset / 2);

                int renderCode = 0;

                if (meow instanceof IDiscoveryCustomRenderer)
                    renderCode = ((IDiscoveryCustomRenderer) meow).getRenderContext();

                boolean unlocked = DiscoveryManager.categoryUnlocked(meow.getKey(), player);

                if (unlocked)
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                else
                    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1F);

                GL11.glEnable(GL11.GL_BLEND);

                int itemX = centreW - (itemDiameter / 2) + xFactor - dragX;
                int itemY = centreH - (itemDiameter) / 2 - 38 - dragY + yOffset;

                if (renderCode != 2 && renderCode != 3) {
                    this.mc.getTextureManager().bindTexture(guiLocation);
                    //drawTexturedModalRect(centreW - 13 + xFactor - dragX, centreH - 46 - dragY + yOffset, 26, 202, 26, 26);
                    drawPartialQuadWithBounds(itemX, itemY, itemDiameter, itemDiameter, 26F / 256F, 202F / 256F, 52F / 256F, 228F / 256F);
                }

                if (Mouse.isButtonDown(0) && !mouse && mx >= itemX && mx <= itemX + itemDiameter && my >= itemY && my <= itemY + itemDiameter && unlocked) {
                    //CLICKED
                    this.mc.displayGuiScreen(new GuiDiscoveryCategory(meow, player));
                }

                RenderItem render = new RenderItem();

                if (renderCode != 1 && renderCode != 3) {
                    if (!unlocked)
                        GL11.glColor4f(0.3F, 0.3F, 0.3F, 1F);
                    if (meow.icon != null) {
                        this.mc.getTextureManager().bindTexture(meow.icon);
                        drawFullQuadWithBounds(centreW - 8 + xFactor - dragX, centreH - 46 - dragY + yOffset, 16, 16);
                    } else if (meow.displayStack != null) {
                        GL11.glEnable(GL_LIGHTING);
                        render.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), meow.displayStack, centreW - 8 + xFactor - dragX, centreH - 46 - dragY + yOffset);
                        GL11.glDisable(GL_LIGHTING);
                    }
                }

                if (renderCode != 0)
                    ((IDiscoveryCustomRenderer) meow).render(xFactor - dragX, dragY + yOffset, mx, my, renderCode);

                itemX = centreW + xFactor - dragX;
                itemY = centreH - 23 - dragY + yOffset;

                float scale = meow.getTitleScale();
                float scaleI = (float) Math.pow(scale, -1);

                GL11.glDisable(GL11.GL_LIGHTING);

                GL11.glTranslatef(itemX, itemY, 0);
                GL11.glScalef(scale, scale, scale);
                if (unlocked)
                    drawCenteredString(this.fontRendererObj, meow.getLocalizedName(), 0, 0, meow.getTitleColour().toInteger());
                else
                    drawCenteredString(this.fontRendererObj, EnumChatFormatting.OBFUSCATED + "blah blah", 0, 0, Colourizer.GRAY.toInteger());
                GL11.glScalef(scaleI, scaleI, scaleI);
                GL11.glTranslatef(-itemX, -itemY, 0);

                GL11.glDisable(GL11.GL_LIGHTING);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(guiLocation);
        this.drawTexturedModalRect(centreW - frameWidth / 2, centreH - frameHeight / 2, 0, 0, frameWidth, frameHeight);

        super.drawScreen(mx, my, par3);

        GL11.glPopMatrix();

        mouse = Mouse.isButtonDown(0);
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