package sourcecoded.quantum.client.renderer.tile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import sourcecoded.quantum.Constants;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.block.BlockInjectedGlass;
import sourcecoded.quantum.block.BlockInjectedStone;
import sourcecoded.quantum.client.renderer.GlowRenderHandler;
import sourcecoded.quantum.tile.TileCornerstone;
import sourcecoded.quantum.tile.TileInjectedGlass;
import sourcecoded.quantum.tile.TileSync;
import sourcecoded.quantum.utils.TessUtils;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class TESRInjectedGlass extends TESRStaticHandler {

    ResourceLocation texDark = new ResourceLocation(Constants.MODID, "textures/blocks/glass.png");
    ResourceLocation texHaze = new ResourceLocation(Constants.MODID, "textures/blocks/glassColour.png");

    @Override
    public void renderTile(TileEntity te, double x, double y, double z, float ptt, boolean isStatic, RenderBlocks renderBlocks) {
        if (!isStatic) {
            glPushMatrix();
            glTranslated(x, y, z);

            glEnable(GL_BLEND);
            glDisable(GL_LIGHTING);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            Tessellator tess = Tessellator.instance;

            float scale = 1.01F;

            tess.startDrawingQuads();

            this.bindTexture(texDark);
            brightness(tess);

            if (te.hasWorldObj())
                tryRender(te, te.getWorldObj(), tess);

            tess.draw();

            tess.startDrawingQuads();

            float[] rgb = ((TileInjectedGlass) te).colour.rgb;
            tess.setColorRGBA_F(rgb[0], rgb[1], rgb[2], GlowRenderHandler.instance().brightness);

            tess.setBrightness(240);
            this.bindTexture(texHaze);

            if (te.hasWorldObj()) {
                tryRender(te, te.getWorldObj(), tess);
            } else {
                tess.setColorRGBA_F(rgb[0], rgb[1], rgb[2], 1F);
                TessUtils.drawCube(tess, 0, 0, 0, 1, 0, 0, 1, 1);
            }

            tess.draw();

            glDisable(GL_BLEND);
            glEnable(GL_LIGHTING);
            glPopMatrix();
        }
    }

    public void tryRender(TileEntity tile, IBlockAccess world, Tessellator tess) {
        ForgeDirection[] sides = new ForgeDirection[]{ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH};
        if (!blockExistsConnectable(tile, world, ForgeDirection.UP)) {
            for (ForgeDirection side : sides)
                if (!blockExists(tile, world, side))
                    TessUtils.drawFace(side, tess, 0F, 15/16F, 0F, 1, 1, 1, 0F, 15/16F, 1, 1);
        }

        if (!blockExistsConnectable(tile, world, ForgeDirection.DOWN)) {
            for (ForgeDirection side : sides)
                if (!blockExists(tile, world, side))
                    TessUtils.drawFace(side, tess, 0, 0, 0, 1, 1/16F, 1, 0, 0, 1, 1/16F);
        }

        for (ForgeDirection dir : sides) {
            if (!blockExistsConnectable(tile, world, dir)) {
                for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                    if (side != dir && side != dir.getOpposite())
                    if (!blockExists(tile, world, side)) {
                        float minX = 0;
                        float maxX = 1;
                        float minY = 0;
                        float maxY = 1;
                        float minZ = 0;
                        float maxZ = 1;

                        if (dir.offsetX == 1) minX = 15/16F; if (dir.offsetX == -1) maxX = 1/16F;
                        if (dir.offsetY == 1) minY = 15/16F; if (dir.offsetY == -1) maxY = 1/16F;
                        if (dir.offsetZ == 1) minZ = 15/16F; if (dir.offsetZ == -1) maxZ = 1/16F;

                        if (side == ForgeDirection.UP || side == ForgeDirection.DOWN) {
                            if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST)
                                TessUtils.drawFace(side, tess, minX, minY, minZ, maxX, maxY, maxZ, minX, minY, maxX, maxY);
                            else
                                TessUtils.drawFace(side, tess, minX, minY, minZ, maxX, maxY, maxZ, minY, minZ, maxY, maxZ);
                        } else {
                            if (side == ForgeDirection.SOUTH || side == ForgeDirection.NORTH)
                                TessUtils.drawFace(side, tess, minX, minY, minZ, maxX, maxY, maxZ, minX, minY, maxX, maxY);
                            else if (side == ForgeDirection.EAST || side == ForgeDirection.WEST)
                                TessUtils.drawFace(side, tess, minX, minY, minZ, maxX, maxY, maxZ, minZ, minY, maxZ, maxY);
                        }
                    }
                }
            }
        }
    }

    public boolean blockExists(TileEntity tile, IBlockAccess world, ForgeDirection direction) {
        Block block = world.getBlock(tile.xCoord + direction.offsetX, tile.yCoord + direction.offsetY, tile.zCoord + direction.offsetZ);
        return block != Blocks.air && (block.isOpaqueCube() || block instanceof BlockInjectedGlass);
    }

    public boolean blockExistsConnectable(TileEntity tile, IBlockAccess world, ForgeDirection direction) {
        boolean flag1 = world.getBlock(tile.xCoord + direction.offsetX, tile.yCoord + direction.offsetY, tile.zCoord + direction.offsetZ) instanceof BlockInjectedGlass;
        boolean flag2 = false;

        TileEntity tile2 = world.getTileEntity(tile.xCoord + direction.offsetX, tile.yCoord + direction.offsetY, tile.zCoord + direction.offsetZ);
        if (tile2 != null && tile2 instanceof TileInjectedGlass && tile instanceof TileInjectedGlass) {
            if (((TileInjectedGlass) tile2).getColour() == ((TileInjectedGlass) tile).getColour())
                flag2 = true;
        }

        return flag1 && flag2;
    }

    public void bindTextureStatic(ResourceLocation location) {
        Minecraft.getMinecraft().renderEngine.bindTexture(location);
    }

    public void resetTexture() {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
    }
}
