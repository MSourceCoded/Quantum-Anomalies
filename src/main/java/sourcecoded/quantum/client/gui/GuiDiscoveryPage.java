package sourcecoded.quantum.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.arrangement.IArrangementRecipe;
import sourcecoded.quantum.api.arrangement.ItemMatrix;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.discovery.*;
import sourcecoded.quantum.api.injection.IInjectorRecipe;
import sourcecoded.quantum.api.injection.InjectorRegistry;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.api.vacuum.IVacuumRecipe;
import sourcecoded.quantum.registry.QABlocks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GuiDiscoveryPage extends GuiScreen {

    public static ResourceLocation pageLocation = new ResourceLocation(Constants.MODID, "textures/gui/book.png");

    int frameWidth = 146;
    int frameHeight = 180;

    int pageIndex = 0;

    DiscoveryItem parentItem;
    DiscoveryPage currentPage;
    GuiDiscoveryCategory parentGui;

    EntityPlayer player;

    boolean mouse = true;

    public GuiDiscoveryPage(DiscoveryItem item, int page, EntityPlayer player, GuiDiscoveryCategory categoryGUI) {
        this.pageIndex = page;
        this.parentItem = item;

        this.currentPage = item.getPages().get(pageIndex);

        this.player = player;

        this.parentGui = categoryGUI;
        if (parentGui == null)
            parentGui = new GuiDiscoveryCategory(DiscoveryRegistry.getCategoryForItem(item.getKey()), player);
    }

    public void keyTyped(char Char, int par2) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_E)) {
            if (isShiftKeyDown())
                this.mc.displayGuiScreen(null);
            else
                this.mc.displayGuiScreen(parentGui);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawScreen(int mx, int my, float par3) {
        int centreW = super.width / 2;
        int centreH = super.height / 2;

        Tessellator tess = Tessellator.instance;

        this.mc.getTextureManager().bindTexture(pageLocation);
        this.drawTexturedModalRect(centreW - frameWidth / 2, centreH - frameHeight / 2 - 10, 20, 1, frameWidth, frameHeight);

        String titleText = "Discovery";
        if (currentPage.title != null)
            titleText = currentPage.localizeTitle();


        float scale = 0.8F;
        float scaleI = (float) Math.pow(scale, -1);

        int itemY = centreH - 78;

        GL11.glTranslatef(centreW, itemY, 0);
        GL11.glScalef(scale, scale, scale);
        drawCenteredStringWOShadow(fontRendererObj, titleText, 0, 0, Colourizer.BLACK.toInteger());
        GL11.glScalef(scaleI, scaleI, scaleI);
        GL11.glTranslatef(-centreW, -itemY, 0);

        GL11.glColor4f(1F, 1F, 1F, 1F);

        int buttonY = centreH + frameHeight / 2 - 30;
        int buttonSizeX = 18;
        int buttonSizeY = 11;

        int buttonBack = centreW - frameWidth / 2 + 10;
        int buttonForward = centreW + frameWidth / 2 - 30;

        this.mc.getTextureManager().bindTexture(pageLocation);

        if (this.parentItem.pages.size() > pageIndex + 1) {
            if (mx >= buttonForward && mx <= buttonForward + buttonSizeX && my >= buttonY && my <= buttonY + buttonSizeY) {
                this.drawTexturedModalRect(buttonForward, buttonY, 26, 194, buttonSizeX, buttonSizeY);

                if (Mouse.isButtonDown(0) && !mouse)
                    this.mc.displayGuiScreen(new GuiDiscoveryPage(this.parentItem, pageIndex + 1, player, parentGui));
            } else this.drawTexturedModalRect(buttonForward, buttonY, 3, 194, buttonSizeX, buttonSizeY);
        }

        if (this.pageIndex > 0) {
            if (mx >= buttonBack && mx <= buttonBack + buttonSizeX && my >= buttonY && my <= buttonY + buttonSizeY) {
                this.drawTexturedModalRect(buttonBack, buttonY, 26, 207, buttonSizeX, buttonSizeY);

                if (Mouse.isButtonDown(0) && !mouse)
                    this.mc.displayGuiScreen(new GuiDiscoveryPage(this.parentItem, pageIndex - 1, player, parentGui));
            } else this.drawTexturedModalRect(buttonBack, buttonY, 3, 207, buttonSizeX, buttonSizeY);
        }

        switch (currentPage.type) {
            case TEXT:
                renderText(centreH - 65);
                break;

            case IMAGE:
                renderImagePage();
                break;

            case CRAFTING:
                renderCrafting(false);
                break;

            case ARRANGEMENT:
                renderCrafting(true);
                break;

            case INJECTION:
                renderInjection();
                break;

            case VACUUM:
                renderVacuum();
                break;
        }

        mouse = Mouse.isButtonDown(0);
    }

    public void renderText(int y) {
        int centreW = super.width / 2;

        int pad = 20;

        FontRenderer renderer = this.fontRendererObj;
        boolean shouldDoUnicode = fontRendererObj.getUnicodeFlag();
        renderer.setUnicodeFlag(true);                          //Makes sure text-scaling isn't an issue

        String textEntry = currentPage.localizeText();

        textEntry = textEntry.replace("<br>", "\n\n");          //Line Breaks

        renderer.FONT_HEIGHT--;

        renderer.drawSplitString(textEntry, centreW - frameWidth / 2 + pad, y, frameWidth - pad * 2, Colourizer.BLACK.toInteger());

        renderer.FONT_HEIGHT++;

        renderer.setUnicodeFlag(shouldDoUnicode);               //Reset unicode
    }

    public void renderImagePage() {
        int centreW = super.width / 2;
        int centreH = super.height / 2;

        int pad = 20;

        this.mc.getTextureManager().bindTexture(this.currentPage.image);

        float ratio = this.currentPage.height / this.currentPage.width;
        int width = frameWidth - pad * 2;

        this.drawFullQuadWithBounds(centreW - frameWidth / 2 + pad, centreH - 60, width, (int) (width * ratio));
        renderText(centreH + 20);
    }

    public void renderCrafting(boolean arrangement) {
        int centreW = super.width / 2;
        int centreH = super.height / 2;

        RenderItem render = new RenderItem();

        this.mc.getTextureManager().bindTexture(pageLocation);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
        GL11.glColor4f(1F, 1F, 1F, 0.07F);
        this.drawTexturedModalRect(centreW - 30, centreH - 50, 46, 186, 61, 61);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        String table = LocalizationUtils.escapeFormatting(QABlocks.ARRANGEMENT.getBlock().getLocalizedName());

        if (!arrangement)
            table = Blocks.crafting_table.getLocalizedName();

        float scale = 0.5F;
        float scaleI = (float) Math.pow(scale, -1);

        GL11.glTranslatef(centreW, centreH - 65, 0);
        GL11.glScalef(scale, scale, scale);
        drawCenteredStringWOShadow(fontRendererObj, EnumChatFormatting.ITALIC + table, 0, 0, Colourizer.BLACK.toInteger());
        GL11.glScalef(scaleI, scaleI, scaleI);
        GL11.glTranslatef(-centreW, -centreH + 65, 0);

        ItemMatrix matrix;
        ItemStack output;
        if (arrangement) {
            IArrangementRecipe recipe = (IArrangementRecipe) currentPage.recipe;
            matrix = recipe.getMatrix();
            output = recipe.getOutput();
        } else {
            matrix = ItemMatrix.createFromRecipe((ShapedRecipes) currentPage.recipe);
            output = ((IRecipe)currentPage.recipe).getRecipeOutput();
        }

        int xStart = centreW - 30;
        int xPadding = 22;
        int yStart = centreH - 49;
        int yPadding = 22;

        if (output != null) {
            this.mc.getTextureManager().bindTexture(pageLocation);
            GL11.glColor4f(1F, 1F, 1F, 0.2F);
            //drawTexturedModalRect(centreW - 3, centreH + 12, 5, 220, 8, 11);
            drawPartialQuadWithBounds(centreW - 4, centreH + 16, 10, 14, 5/256F, 220/256F, 13/256F, 231/256F);

            renderItemAndOverlay(render, fontRendererObj, this.mc.getTextureManager(), output, centreW - 7, centreH + 32);
        }

        if (matrix != null) {
            for (int i = 0; i < matrix.getWidth(); i++)
                for (int j = 0; j < matrix.getHeight(); j++) {
                    ItemStack stack = matrix.getItemAt(j, i);               //Why is this backwards? Idk, I screwed something up. Oh well, idc
                    if (stack == null) continue;

                    renderItemAndOverlay(render, fontRendererObj, this.mc.getTextureManager(), stack, xStart + xPadding * i, yStart + yPadding * j);
                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    GL11.glDisable(GL11.GL_LIGHTING);
                }
        }
    }

    public void renderInjection() {
        int centreW = super.width / 2;
        int centreH = super.height / 2;

        RenderItem render = new RenderItem();

        float scale = 0.5F;
        float scaleI = (float) Math.pow(scale, -1);

        GL11.glTranslatef(centreW, centreH - 65, 0);
        GL11.glScalef(scale, scale, scale);
        drawCenteredStringWOShadow(fontRendererObj, EnumChatFormatting.ITALIC + LocalizationUtils.escapeFormatting(QABlocks.RIFT_INJECTION_POOL.getBlock().getLocalizedName()), 0, 0, Colourizer.BLACK.toInteger());
        GL11.glScalef(scaleI, scaleI, scaleI);
        GL11.glTranslatef(-centreW, -centreH + 65, 0);

        this.mc.getTextureManager().bindTexture(pageLocation);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
        GL11.glColor4f(1F, 1F, 1F, 0.2F);
        drawPartialQuadWithBounds(centreW - 26, centreH - 40, 14, 10, 28 / 256F, 222 / 256F, 39 / 256F, 230 / 256F);
        drawPartialQuadWithBounds(centreW + 11, centreH - 40, 14, 10, 28/256F, 222/256F, 39/256F, 230/256F);
        render.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(QABlocks.RIFT_INJECTION_POOL.getBlock()), centreW - 8, centreH - 44);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        IInjectorRecipe recipe = (IInjectorRecipe) currentPage.recipe;

        DecimalFormat format = new DecimalFormat("#,##0");

        if (recipe != null) {
            GL11.glTranslatef(centreW, centreH - 25, 0);
            GL11.glScalef(scale, scale, scale);
            drawCenteredStringWOShadow(fontRendererObj, format.format(recipe.getEnergyRequired()) + " QRE", 0, 0, Colourizer.BLACK.toInteger());
            GL11.glScalef(scaleI, scaleI, scaleI);
            GL11.glTranslatef(-centreW, -centreH + 25, 0);

            renderItemAndOverlay(render, fontRendererObj, mc.getTextureManager(), recipe.getInput(), centreW - 44, centreH - 44);
            renderItemAndOverlay(render, fontRendererObj, mc.getTextureManager(), recipe.getOutput(), centreW + 28, centreH - 44);
        }
    }

    public void renderVacuum() {
        int centreW = super.width / 2;
        int centreH = super.height / 2;

        RenderItem render = new RenderItem();

        float scale = 0.5F;
        float scaleI = (float) Math.pow(scale, -1);

        GL11.glTranslatef(centreW, centreH - 65, 0);
        GL11.glScalef(scale, scale, scale);
        String translation = "qa.vacuum.chamber.name";
        drawCenteredStringWOShadow(fontRendererObj, EnumChatFormatting.ITALIC + LocalizationUtils.escapeFormatting(LocalizationUtils.translateLocalWithColours(translation, translation)), 0, 0, Colourizer.BLACK.toInteger());
        GL11.glScalef(scaleI, scaleI, scaleI);
        GL11.glTranslatef(-centreW, -centreH + 65, 0);

        GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
        GL11.glColor4f(1F, 1F, 1F, 0.11F);
        this.mc.getTextureManager().bindTexture(pageLocation);
        drawPartialQuadWithBounds(centreW - 47, centreH - 50, 100, 100, 118 / 256F, 193 / 256F, 168 / 256F, 243 / 256F);
        GL11.glColor4f(1F, 1F, 1F, 1F);

        IVacuumRecipe recipe = (IVacuumRecipe) currentPage.recipe;

        DecimalFormat format = new DecimalFormat("#,##0");

        if (recipe != null) {
            GL11.glTranslatef(centreW, centreH + 50, 0);
            GL11.glScalef(scale, scale, scale);
            drawCenteredStringWOShadow(fontRendererObj, format.format(recipe.getVacuumEnergyStart()) + " QRE (Start)", 0, 0, Colourizer.BLACK.toInteger());
            drawCenteredStringWOShadow(fontRendererObj, format.format(recipe.getVacuumEnergyPerItem()) + " QRE (Per Item)", 0, 13, Colourizer.BLACK.toInteger());
            GL11.glScalef(scaleI, scaleI, scaleI);
            GL11.glTranslatef(-centreW, -centreH - 50, 0);

            GL11.glTranslatef(centreW, centreH - 50, 0);
            GL11.glScalef(scale, scale, scale);
            drawCenteredStringWOShadow(fontRendererObj, "Catalysts", 0, 0, Colourizer.BLACK.toInteger());
            drawCenteredStringWOShadow(fontRendererObj, "Inputs", 0, 60, Colourizer.BLACK.toInteger());
            drawCenteredStringWOShadow(fontRendererObj, "Outputs", 0, 130, Colourizer.BLACK.toInteger());
            GL11.glScalef(scaleI, scaleI, scaleI);
            GL11.glTranslatef(-centreW, -centreH + 50, 0);

            int perOffsetX = 20;
            int perOffsetY = 16;

            List<List<ItemStack>> inputs = splitStackList(recipe.getIngredients());
            List<List<ItemStack>> catalysts = splitStackList(recipe.getCatalysts());
            List<List<ItemStack>> output = splitStackList(recipe.getOutputs());

            float itemScale = 0.8F;
            float itemScaleI = (float) Math.pow(itemScale, -1);

            GL11.glTranslatef(centreW, centreH, 0);
            GL11.glScalef(itemScale, itemScale, itemScale);
            for (List<ItemStack> stackList : inputs) {
                int totalOffset = perOffsetX * (stackList.size() + 1);
                int yOffset = perOffsetY * inputs.indexOf(stackList) + 26;
                for (ItemStack stack : stackList) {
                    int xFactor = perOffsetX * (stackList.indexOf(stack) + 1);
                    xFactor -= (totalOffset / 2);

                    int itemX = - 8 + xFactor;
                    int itemY = - 8 - 38 + yOffset;

                    renderItemAndOverlay(itemRender, fontRendererObj, mc.getTextureManager(), stack, itemX, itemY);
                }
            }

            for (List<ItemStack> stackList : catalysts) {
                int totalOffset = perOffsetX * (stackList.size() + 1);
                int yOffset = perOffsetY * catalysts.indexOf(stackList) - 11;
                for (ItemStack stack : stackList) {
                    int xFactor = perOffsetX * (stackList.indexOf(stack) + 1);
                    xFactor -= (totalOffset / 2);

                    int itemX = - 8 + xFactor;
                    int itemY = - 8 - 38 + yOffset;

                    renderItemAndOverlay(itemRender, fontRendererObj, mc.getTextureManager(), stack, itemX, itemY);
                }
            }

            for (List<ItemStack> stackList : output) {
                int totalOffset = perOffsetX * (stackList.size() + 1);
                int yOffset = perOffsetY * output.indexOf(stackList) + 71;
                for (ItemStack stack : stackList) {
                    int xFactor = perOffsetX * (stackList.indexOf(stack) + 1);
                    xFactor -= (totalOffset / 2);

                    int itemX = - 8 + xFactor;
                    int itemY = - 8 - 38 + yOffset;

                    renderItemAndOverlay(itemRender, fontRendererObj, mc.getTextureManager(), stack, itemX, itemY);
                }
            }

            GL11.glScalef(itemScaleI, itemScaleI, itemScaleI);
            GL11.glTranslatef(-centreW, -centreH, 0);
        }

    }

    public List<List<ItemStack>> splitStackList(List<ItemStack> stacks) {
        List<List<ItemStack>> list = new ArrayList<List<ItemStack>>();

        int index = 0;
        int level = 0;
        for (ItemStack stack : stacks) {
            if (index == 7) {
                level++;
                index = 0;
            }
            if (list.size() == level || list.get(level) == null)
                list.add(new ArrayList<ItemStack>());

            list.get(level).add(stack);
            index++;
        }

        return list;
    }

    public void renderItemAndOverlay(RenderItem render, FontRenderer f, TextureManager tex, ItemStack stack, int x, int y) {
        RenderHelper.disableStandardItemLighting();
        render.renderItemAndEffectIntoGUI(f, tex, stack, x, y);
        render.renderItemOverlayIntoGUI(f, tex, stack, x, y);
    }

    public void drawCenteredStringWOShadow(FontRenderer fontRenderer, String string, int x, int y, int colour) {
        fontRenderer.drawString(string, x - fontRenderer.getStringWidth(string) / 2, y, colour);
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