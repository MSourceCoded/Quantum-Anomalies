package sourcecoded.quantum.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;

import static org.lwjgl.opengl.GL11.*;

public class ItemSceptreRenderer implements IItemRenderer {

    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Constants.MODID, "model/sceptre/sceptre.obj"));
    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/infusedStone.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/haze.png");
    ResourceLocation texBlank = new ResourceLocation(Constants.MODID, "textures/misc/blank.png");

    String[] probes = new String[] {"Probe01", "Probe02", "Probe03", "Probe04", "Probe05", "Probe06", "Probe06", "Probe07", "Probe08"};
    String[] caps = new String[] {"Cap1", "Cap1_5", "Cap2", "Cap2_5"};

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        glPushMatrix();
        //glTranslated(x + 0.5, y + 0.1, z + 0.5);
        glTranslatef(0F, -0.5F, 0F);

        glDisable(GL_LIGHTING);
        float lastBrightX = OpenGlHelper.lastBrightnessX;
        float lastBrightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        EntityLivingBase entity = null;
        if ((type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            entity = (EntityLivingBase)data[1];
        }

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            glTranslatef(1F, -1F, 0.3F);
            useCheck(item, type, entity);
        } else if (type == ItemRenderType.INVENTORY) {
            glTranslatef(-0.45F, -0.1F, 0.45F);
            glRotatef(45F, -1F, 0F, -1F);
        } else if (type == ItemRenderType.ENTITY) {
            glTranslatef(0F, 0.5F, 0F);
            glRotatef(5F, 1F, 0F, 0F);
        } else {
            glTranslatef(1.7F, 0.6F, 1.7F);
            glRotatef(55F, -1F, 0F, 1F);

            useCheck(item, type, entity);
        }

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        float scale = 0.15F;
        if (type == ItemRenderType.INVENTORY) scale = 0.07F;
        if (type == ItemRenderType.ENTITY) scale = 0.08F;
        glScalef(scale, scale, scale);


        Minecraft.getMinecraft().renderEngine.bindTexture(texDark);
        model.renderPart("Core");
        model.renderOnly(caps);

        Minecraft.getMinecraft().renderEngine.bindTexture(texBlank);
        float red = 0.8F;
        float green = 0F;
        float blue = 0.8F;
        float alpha = 0.6F;

        if (item.stackTagCompound != null && item.stackTagCompound.hasKey("colourData")) {
            NBTTagCompound compound = item.stackTagCompound.getCompoundTag("colourData");
            red = compound.getFloat("r");
            green = compound.getFloat("g");
            blue = compound.getFloat("b");
        }

        //glColor4f(red, green, blue, alpha);
        glColor4f(red, green, blue, Math.min(GlowRenderHandler.instance().brightness, alpha));

        model.renderOnly(probes);
        model.renderPart("Box001");

        glDisable(GL_BLEND);

        glPopMatrix();
        glEnable(GL_LIGHTING);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightX, lastBrightY);
    }

    public void useCheck(ItemStack item, ItemRenderType type, EntityLivingBase player) {
        if (player != null && player instanceof EntityPlayer && ((EntityPlayer) player).getItemInUse() != null) {
            if (type == ItemRenderType.EQUIPPED)
                glRotatef(-25F, 1F, 0F, -1F);
            else
                glRotatef(-30F, 1F, 0F, 1F);
        }
    }
}
