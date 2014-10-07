package sourcecoded.quantum.client.renderer.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.item.tools.ItemRiftBow;

import static org.lwjgl.opengl.GL11.*;

public class ItemBowRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        glPushMatrix();

        if (!item.hasTagCompound()) item.stackTagCompound = new NBTTagCompound();
        if (!item.stackTagCompound.hasKey("iconIndex")) item.stackTagCompound.setInteger("iconIndex", -1);
        if (!item.stackTagCompound.hasKey("inUse")) item.stackTagCompound.setBoolean("inUse", false);

        TexDepthMap[] map = ItemRiftBow.getDepthMap(item.stackTagCompound.getInteger("iconIndex"));

        boolean isTool = true;
        boolean inUse = item.stackTagCompound.getBoolean("inUse");

        EntityLivingBase entity = null;
        if ((type == ItemRenderType.EQUIPPED) || (type == ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            entity = (EntityLivingBase)data[1];
        }

        float scale = 1.5F;

        if (type == ItemRenderType.ENTITY)
            scale = 1F;
        else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
            scale = 1.5F;

        if (type == ItemRenderType.EQUIPPED)
            scale = 3F;

        if (item.getItem() instanceof ItemSword && type == ItemRenderType.ENTITY)
            scale = 0.85F;

        //glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        //GL11.glShadeModel(7425);

        glScalef(scale, scale, scale);

        if (type == ItemRenderType.ENTITY) {
            glRotatef(90F, 0F, 1F, 0F);
            glTranslatef(-0.5F, -0.45F, 0F);
        } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON){
            glRotatef(40F, 0F, 1F, 0F);
            glRotatef(55F, 0F, 0F, 1F);
            glRotatef(170F, -1F, 0F, 0F);
            glRotatef(90F, 0F, 0F, -1F);
            glTranslatef(0.25F, -0.1F, -0.2F);
            if (inUse) {
                glTranslatef(0F, 0.12F, -0.2F);
            }
        } else if (type == ItemRenderType.EQUIPPED) {
            glRotatef(45F, -1F, 0F, 0F);
            glRotatef(45F, 0F, 1F, 1F);
            glRotatef(90F, 0F, -1F, 0F);
            glRotatef(60F, 0F, 0F, 1F);
            glTranslatef(-0.2F, -1F, 0F);
        }

        Tessellator tess = Tessellator.instance;

        for (TexDepthMap de : map)
                de.renderDepth(tess);


        //GL11.glShadeModel(7424);
        GL11.glDisable(3042);

        //glDisable(GL_BLEND);

        glPopMatrix();
    }
}
