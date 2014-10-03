package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileRiftInjector;
import sourcecoded.quantum.util.TessUtils;

import static org.lwjgl.opengl.GL11.*;

public class TESRRiftInjector extends TESRStaticHandler {

    WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/block/infuser.obj"));
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

            TileRiftInjector injector = (TileRiftInjector) te;

            if (injector != null && injector.hasWorldObj() && injector.currentItem != null) {
                EntityItem item = new EntityItem(injector.getWorldObj(), 0D, 0D, 0D, injector.currentItem);
                item.hoverStart = 0.0F;
                RenderItem.renderInFrame = true;

                RenderManager.instance.renderEntityWithPosYaw(item, 0.5D, 0.35D, 0.5D, 0F, 0F);

                RenderItem.renderInFrame = false;
            }

            glColor4f(1F, 1F, 1F, 1F);
            tess.startDrawingQuads();
            tess.setColorRGBA_F(1F, 1F, 1F, GlowRenderHandler.instance().brightness);
            tess.setBrightness(240);
            this.bindTexture(texHaze);

            float percentage = 0F;

            if (injector != null) {
                try {
                    percentage = (float)injector.getRiftEnergy() / (float)injector.getMaxRiftEnergy();
                    tess.setColorRGBA_F(injector.colour.rgb[0], injector.colour.rgb[1], injector.colour.rgb[2], GlowRenderHandler.instance().brightness);
                } catch (ArithmeticException exception) {
                    //Divided by 0
                }
            }

            float minimum = 3.9F/16F;

            float height = percentage * 4.5F/16F + minimum;
            TessUtils.drawFace(ForgeDirection.UP, tess, 1/16F, height, 1/16F, 1-1/16F, height, 1-1/16F, 0, 0, 1, 1);

            tess.draw();

            glEnable(GL_LIGHTING);
            glPopMatrix();
        } else {
            Tessellator tess = Tessellator.instance;

            x += 0.5;
            z += 0.5;

            tess.addTranslation((float) x, (float) y, (float) z);
            tess.startDrawingQuads();
            tess.setColorRGBA_F(1F, 1F, 1F, 1F);
            brightness(tess);
            Minecraft.getMinecraft().renderEngine.bindTexture(texDark);
            model.tessellatePart(tess, "Base");
            tess.draw();
            tess.addTranslation((float) -x, (float) -y, (float) -z);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
    }

}
