package sourcecoded.quantum.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.common.util.ForgeDirection;

public class TessUtils {

    public static void drawCube(Tessellator tess, double minX, double minY, double minZ, double size, double u, double v, double U, double V) {
        double maxX = minX + size;
        double maxY = minY + size;
        double maxZ = minZ + size;

        drawFace(ForgeDirection.DOWN, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
        drawFace(ForgeDirection.UP, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
        drawFace(ForgeDirection.SOUTH, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
        drawFace(ForgeDirection.NORTH, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
        drawFace(ForgeDirection.EAST, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
        drawFace(ForgeDirection.WEST, tess, minX, minY, minZ, maxX, maxY, maxZ, u, v, U, V);
    }

    public static void drawFace(ForgeDirection direction, Tessellator tess, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double u, double v, double U, double V) {
        if (direction == ForgeDirection.DOWN) {
            tess.addVertexWithUV(maxX, minY, minZ, U, v);       //Down
            tess.addVertexWithUV(maxX, minY, maxZ, U, V);
            tess.addVertexWithUV(minX, minY, maxZ, u, V);
            tess.addVertexWithUV(minX, minY, minZ, u, v);
        } else if (direction == ForgeDirection.UP) {
            tess.addVertexWithUV(minX, maxY, maxZ, u, V);       //Up
            tess.addVertexWithUV(maxX, maxY, maxZ, U, V);
            tess.addVertexWithUV(maxX, maxY, minZ, U, v);
            tess.addVertexWithUV(minX, maxY, minZ, u, v);
        } else if (direction == ForgeDirection.SOUTH) {
            tess.addVertexWithUV(maxX, minY, maxZ, U, V);       //South
            tess.addVertexWithUV(maxX, maxY, maxZ, U, v);
            tess.addVertexWithUV(minX, maxY, maxZ, u, v);
            tess.addVertexWithUV(minX, minY, maxZ, u, V);
        } else if (direction == ForgeDirection.NORTH) {
            tess.addVertexWithUV(minX, maxY, minZ, u, v);       //North
            tess.addVertexWithUV(maxX, maxY, minZ, U, v);
            tess.addVertexWithUV(maxX, minY, minZ, U, V);
            tess.addVertexWithUV(minX, minY, minZ, u, V);
        } else if (direction == ForgeDirection.WEST) {
            tess.addVertexWithUV(minX, minY, minZ, U, V);       //WEST
            tess.addVertexWithUV(minX, minY, maxZ, u, V);
            tess.addVertexWithUV(minX, maxY, maxZ, u, v);
            tess.addVertexWithUV(minX, maxY, minZ, U, v);
        } else if (direction == ForgeDirection.EAST) {
            tess.addVertexWithUV(maxX, maxY, minZ, u, v);       //EAST
            tess.addVertexWithUV(maxX, maxY, maxZ, U, v);
            tess.addVertexWithUV(maxX, minY, maxZ, U, V);
            tess.addVertexWithUV(maxX, minY, minZ, u, V);
        }
    }
}
