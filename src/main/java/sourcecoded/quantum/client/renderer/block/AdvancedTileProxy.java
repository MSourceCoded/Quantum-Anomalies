package sourcecoded.quantum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import sourcecoded.quantum.client.renderer.tile.TESRStaticHandler;

import static org.lwjgl.opengl.GL11.*;

public class AdvancedTileProxy implements ISimpleBlockRenderingHandler{

    public static final int renderID = RenderingRegistry.getNextAvailableRenderId();

    TileEntity te;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderBlocks) {
        if (block instanceof ITileEntityProvider) {
            ITileEntityProvider prov = (ITileEntityProvider) block;
            te = prov.createNewTileEntity(null, metadata);
        } else return;

        if (block instanceof IBlockRenderHook) {
            IBlockRenderHook hook = (IBlockRenderHook) block;
            hook.callbackInventory(te);
        }

        glRotatef(90F, 0F, 1F, 0F);
        glTranslatef(-0.5F, -0.5F, -0.5F);
        float scale = 1F;
        glScalef(scale, scale, scale);

        TESRStaticHandler renderer = (TESRStaticHandler) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(te.getClass());
        renderer.renderTile(te, 0.0, 0.0, 0.0, 0, true);

        renderer.renderTile(te, 0.0, 0.0, 0.0, 0, false);

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks) {
        if (block instanceof ITileEntityProvider) {
            ITileEntityProvider prov = (ITileEntityProvider) block;
            te = prov.createNewTileEntity(null, world.getBlockMetadata(x, y, z));
        } else return false;

        Tessellator.instance.draw();
        TESRStaticHandler renderer = (TESRStaticHandler) TileEntityRendererDispatcher.instance.mapSpecialRenderers.get(te.getClass());
        renderer.renderTile(te, x, y, z, 0, true);
        Tessellator.instance.startDrawingQuads();

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderID;
    }

}
