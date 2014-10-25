package sourcecoded.quantum.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemTransparencyRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return (helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION) || (helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glEnable(2896);
        GL11.glEnable(2929);

        if (type != ItemRenderType.INVENTORY) return;

        GL11.glScalef(16.0F, 16.0F, 1.0F);

        Tessellator tessellator = Tessellator.instance;
        IIcon icon = item.getItem().getIcon(item, 0);

        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3008);

        GL11.glShadeModel(7425);

        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.03125D, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.03125D, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(1.0D, 1.0D, 0.03125D, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(1.0D, 0.0D, 0.03125D, icon.getMaxU(), icon.getMinV());
        tessellator.draw();

        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);

        GL11.glScalef(0.0625F, 0.0625F, 1.0F);
    }
}
