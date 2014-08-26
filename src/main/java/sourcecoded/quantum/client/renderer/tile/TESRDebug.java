package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import sourcecoded.quantum.Constants;

import static org.lwjgl.opengl.GL11.*;

public class TESRDebug extends TileEntitySpecialRenderer {

    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/sceptre/sceptre.obj"));
    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/haze.png");
    ResourceLocation texBlank = new ResourceLocation(Constants.MODID, "textures/misc/blank.png");

    String[] probes = new String[] {"Probe01", "Probe02", "Probe03", "Probe04", "Probe05", "Probe06", "Probe06", "Probe07", "Probe08"};
    String[] caps = new String[] {"Cap1", "Cap1_5", "Cap2", "Cap2_5"};

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
        glPushMatrix();
        glTranslated(x + 0.5, y + 0.1, z + 0.5);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float scale = 0.05F;
        glScalef(scale, scale, scale);


        this.bindTexture(texDark);
        model.renderPart("Core");
        model.renderOnly(caps);

        this.bindTexture(texBlank);

        float red = 0.5F;
        float green = 0F;
        float blue = 0.7F;
        float alpha = 0.4F;

        glColor4f(red, green, blue, alpha);

        model.renderOnly(probes);
        model.renderPart("Box001");

        glPopMatrix();
    }

}
