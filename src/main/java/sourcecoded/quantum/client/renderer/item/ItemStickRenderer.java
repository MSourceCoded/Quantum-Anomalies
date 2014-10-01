package sourcecoded.quantum.client.renderer.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class ItemStickRenderer implements IItemRenderer {

    TexDepthMap[] depths;

    public ItemStickRenderer(TexDepthMap... params) {
        depths = params;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.INVENTORY && item.getItemDamage() == 1;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        glPushMatrix();

        boolean isTool = true;

        EntityLivingBase entity = null;
        if ((type == ItemRenderType.EQUIPPED) || (type == ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            entity = (EntityLivingBase)data[1];
        }

        float scale = 1.5F;

        if (type == ItemRenderType.ENTITY)
            scale = 1F;
        else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
            scale = 1.5F;

        if (isTool) {
            if (type == ItemRenderType.EQUIPPED)
                scale = 3F;
        }

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
            if (isTool) {
                glRotatef(40F, 0F, 1F, 0F);
                glRotatef(50F, 0F, 0F, 1F);
                glRotatef(170F, -1F, 0F, 0F);
                glRotatef(90F, 0F, 0F, -1F);
                glTranslatef(0.25F, -0.1F, -0.2F);
            } else {
                GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-1F, 0F, 0.5F);
            }
        } else if (type == ItemRenderType.EQUIPPED) {
            if (isTool) {
                glRotatef(45F, -1F, 0F, 0F);
                glRotatef(45F, 0F, 1F, 1F);
                glRotatef(90F, 0F, -1F, 0F);
                glRotatef(60F, 0F, 0F, 1F);
                glTranslatef(-0.1F, -0.7F, 0F);
            } else {
                glTranslatef(0.5F, 0.125F, 1F);
                glRotatef(90F, -1F, 1F, 1F);
            }
        }

        Tessellator tess = Tessellator.instance;

        for (TexDepthMap de : depths)
                de.renderDepth(tess);


        //GL11.glShadeModel(7424);
        GL11.glDisable(3042);

        //glDisable(GL_BLEND);

        glPopMatrix();
    }
}
