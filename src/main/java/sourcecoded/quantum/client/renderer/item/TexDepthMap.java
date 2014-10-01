package sourcecoded.quantum.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

public class TexDepthMap {

    ResourceLocation tex;
    float depth;

    public TexDepthMap(ResourceLocation tex, float depth) {
        this.tex = tex;
        this.depth = depth;
    }

    public void renderDepth(Tessellator tess) {
        Minecraft.getMinecraft().renderEngine.bindTexture(tex);
        int height = glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);
        int width = glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);

        float translate = depth / 2F;

        glTranslated(0F, 0F, translate);

        ItemRenderer.renderItemIn2D(tess, 0, 0, 1, 1, height, width, depth);

        glTranslated(0F, 0F, -translate);
    }
}
