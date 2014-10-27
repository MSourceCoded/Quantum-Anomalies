package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileArrangement;
import sourcecoded.quantum.util.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRArrangement extends TESRStaticHandler {

    WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/block/arranger.obj"));
    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/hazeDesaturated.png");

    @Override
    public void renderTile(TileEntity te, double x, double y, double z, float ptt, boolean isStatic, RenderBlocks renderBlocks) {
        if (!isStatic) {
            glPushMatrix();
            glTranslated(x, y, z);

            glEnable(GL_BLEND);
            glDisable(GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            Tessellator tess = Tessellator.instance;

            float innerPadding = 0.03F;

            tess.startDrawingQuads();

            float[] rgb = ((TileArrangement) te).colour.rgb;

            tess.setColorRGBA_F(rgb[0], rgb[1], rgb[2], GlowRenderHandler.instance().brightness);
            tess.setBrightness(240);
            this.bindTexture(texHaze);
            TessUtils.drawCube(tess, innerPadding, innerPadding, innerPadding, 1F - (innerPadding * 2), 0, 0, 1, 1);
            tess.draw();

            TileArrangement arrangement = (TileArrangement) te;
            if (arrangement.hasWorldObj() && arrangement.activeRecipe != null) {
                float speed = 0.0025F;
                arrangement.renderProgress += ptt * speed;

                if (arrangement.renderProgress >= 1F)
                    arrangement.renderProgress = 0F;

                ItemStack items = arrangement.getOutput().copy();

                items.stackSize = 1;            //Display only one

                EntityItem item = new EntityItem(arrangement.getWorldObj(), 0D, 0D, 0D, items);
                item.hoverStart = 0.0F;
                RenderItem.renderInFrame = true;

                double yo = 1.5D + 0.125*Math.sin(arrangement.renderProgress * 2 * Math.PI);
                RenderManager.instance.renderEntityWithPosYaw(item, 0.5D, yo, 0.5D, 0F, 0F);

                RenderItem.renderInFrame = false;
            }

            glEnable(GL_LIGHTING);
            glPopMatrix();
        } else {
            Tessellator tess = Tessellator.instance;

            x += 0.5;
            y += 0.5;
            z += 0.5;

            tess.addTranslation((float) x, (float) y, (float) z);
            tess.startDrawingQuads();
            tess.setColorRGBA_F(1F, 1F, 1F, 1F);
            brightness(tess);
            Minecraft.getMinecraft().renderEngine.bindTexture(texDark);
            model.tessellateAll(tess);
            tess.draw();
            tess.addTranslation((float) -x, (float) -y, (float) -z);

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
    }

}
