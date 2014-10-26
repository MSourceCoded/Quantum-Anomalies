package sourcecoded.quantum.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import sourcecoded.quantum.api.Point3D;
import sourcecoded.quantum.api.block.BlockWithData;
import sourcecoded.quantum.util.save.QAWorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class QuantumLockRender {

    public static HashMap<Point3D, BlockWithData> lockCache = new HashMap<Point3D, BlockWithData>();

    public static void refreshCache(QAWorldSavedData data) {
        NBTTagList list = data.lockList;
        lockCache.clear();
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            int x = compound.getInteger("x");
            int y = compound.getInteger("y");
            int z = compound.getInteger("z");
            Point3D point = new Point3D(x, y, z);
            Block block = Block.getBlockFromName(compound.getString("blockID"));
            int meta = compound.getInteger("meta");

            lockCache.put(point, new BlockWithData(block, meta));
        }
    }

    //Called in the particle dispatcher
    public static void renderTick(RenderWorldLastEvent event) {
        RenderGlobal renderContext = event.context;
        float ptt = event.partialTicks;

        for (Map.Entry<Point3D, BlockWithData> entry : lockCache.entrySet()) {
            Block block = entry.getValue().block;
            Point3D point = entry.getKey();
            TileEntity tile = Minecraft.getMinecraft().theWorld.getTileEntity(point.getX(), point.getY(), point.getZ());

            if (block == null) return;

            if (block.renderAsNormalBlock()) {
                RenderBlocks.getInstance().renderStandardBlock(block, point.getX(), point.getY(), point.getZ());
            } else {
                if (block.getRenderType() == -1) {
                    if (tile != null) {
                        TileEntitySpecialRenderer renderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tile);
                        if (renderer != null)
                            renderer.renderTileEntityAt(tile, point.getX(), point.getY(), point.getZ(), ptt);
                    }
                } else {
                    Tessellator.instance.startDrawingQuads();
                    //RenderingRegistry.instance().renderWorldBlock(RenderBlocks.getInstance(), Minecraft.getMinecraft().theWorld, point.getX(), point.getY(), point.getZ(), block, block.getRenderType());
                    System.err.println("WORLD: " + Minecraft.getMinecraft().theWorld);
                    RenderBlocks.getInstance().blockAccess = Minecraft.getMinecraft().theWorld;
                    RenderBlocks.getInstance().renderBlockByRenderType(block, point.getX(), point.getY(), point.getZ());
                    Tessellator.instance.draw();
                }
            }
        }
    }

}
